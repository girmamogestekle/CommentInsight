# Gateway 404 fix — patch apply guide

Patches in this folder:

| File | Target repo |
|------|-------------|
| `comment-insight-config-api-gateway-service-dev.patch` | `comment-insight-config` |
| `api-gateway-service-dev.properties` | Full replacement file (alternative to patch) |
| `api-gateway-fallback-controller.patch` | `CommentInsight` (already applied in workspace) |

## 1. Config repo (`comment-insight-config`)

From your config repo root:

```bash
git apply /path/to/CommentInsight/patches/comment-insight-config-api-gateway-service-dev.patch
# Or copy the full file:
# cp /path/to/CommentInsight/patches/api-gateway-service-dev.properties api-gateway-service/

git commit -m "Fix gateway timeouts, fallback URIs, and sentiment route index"
git push
```

Then restart **config-server** and **api-gateway-service** (or `POST /actuator/refresh` if refresh is enabled).

### What the config patch changes

| Area | Before | After |
|------|--------|-------|
| Timeouts | Default (~1s TimeLimiter) | `120s` for gateway HTTP client + each circuit breaker |
| Connector fallback | `forward:/fallback/connector-service` | `forward:/api/fallback/connector-service` |
| YouTube fallback | `forward:/fallback/youtube-connector-service` | `forward:/api/fallback/youtube-connector-service` |
| Sentiment route filters | Incorrectly used `routes[1]` (overwrote YouTube CB) | Fixed to `routes[2]` + `/api/fallback/sentiment-service` |

> **Note:** If your config server still serves an older revision (e.g. YouTube route uses `sentimentCircuitBreaker`), push this patch and restart config-server so the gateway picks up the corrected file.

## 2. API Gateway (`CommentInsight` repo)

`FallbackController` is already updated in this workspace. To apply the same change elsewhere:

```bash
cd CommentInsight
git apply patches/api-gateway-fallback-controller.patch
```

Changes:

- `@GetMapping` → `@RequestMapping` so circuit-breaker fallbacks work for **POST** `/analyze`
- Added `/api/fallback/sentiment-service` handler

Rebuild and restart **api-gateway-service**.

## 3. Verify

```bash
curl http://localhost:8080/api/youtube/v1/health

curl -X POST http://localhost:8080/api/youtube/v1/analyze \
  -H "Content-Type: application/json" \
  -d '{"videoUrl":"https://www.youtube.com/watch?v=dQw4w9WgXcQ","comments":5}'
```

Expect **200** from analyze (may take several seconds).
