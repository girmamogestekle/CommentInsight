# Secrets with Doppler

CommentInsight injects runtime credentials into Docker Compose from **[Doppler](https://www.doppler.com)** (free Developer plan). Secrets are not stored in Docker Hub images.

## Secret names (create these in Doppler)

| Name | Used by |
|------|---------|
| `YOUTUBE_API_KEY` | youtube-connector-service |
| `OPENAI_API_KEY` | sentiment-service |
| `CONFIG_REPO_URL` | config-server |
| `CONFIG_REPO_BRANCH` | config-server (default `main`) |
| `CONFIG_REPO_USERNAME` | config-server |
| `CONFIG_REPO_TOKEN` | config-server |
| `DOCKER_HUB_USERNAME` | GitHub Actions (`docker-publish.yml`) |
| `DOCKER_HUB_TOKEN` | GitHub Actions (`docker-publish.yml`) |

Compose secrets match the `${...}` variables in [`docker-compose.yml`](../docker-compose.yml). Hub credentials are used only by CI.

## One-time setup

### 1. Create the Doppler project

1. Sign up / log in at [dashboard.doppler.com](https://dashboard.doppler.com)
2. Create project: **`commentinsight`**
3. Open config **`dev`**
4. Add the secrets listed above (your real values)

### 2. Install the CLI

macOS:

```bash
brew install dopplerhq/cli/doppler
```

Other OS: [Install CLI](https://docs.doppler.com/docs/install-cli)

```bash
doppler login
```

### 3. Link this repo

From the repository root (where [`doppler.yaml`](../doppler.yaml) lives):

```bash
doppler setup
# Project: commentinsight
# Config:  dev
```

`doppler.yaml` already suggests `commentinsight` / `dev`.

## Run the stack with Doppler

```bash
# Pull images from Docker Hub, then start with secrets injected
./scripts/doppler-compose.sh pull
./scripts/doppler-compose.sh up -d

# Or equivalently:
doppler run -- docker compose pull
doppler run -- docker compose up -d
```

Stop:

```bash
./scripts/doppler-compose.sh down
```

Verify secrets are visible to Compose (values redacted in Doppler UI; this only checks names resolve):

```bash
doppler secrets
```

## How it works

```text
Doppler (cloud)  --doppler run-->  docker compose  -->  containers
                                      │
                                      ├─ YOUTUBE_API_KEY
                                      ├─ OPENAI_API_KEY
                                      └─ CONFIG_REPO_*
```

`doppler run` puts secrets into the process environment. Compose substitutes them into each service’s `environment:` block. Nothing is baked into Hub images.
