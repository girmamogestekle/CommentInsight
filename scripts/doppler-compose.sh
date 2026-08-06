#!/usr/bin/env bash
# Run docker compose with secrets injected from Doppler.
# Usage: ./scripts/doppler-compose.sh up -d
#        ./scripts/doppler-compose.sh pull
#        ./scripts/doppler-compose.sh down

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR"

if ! command -v doppler >/dev/null 2>&1; then
  echo "Doppler CLI not found. Install: https://docs.doppler.com/docs/install-cli"
  exit 1
fi

if ! doppler me >/dev/null 2>&1; then
  echo "Not logged in to Doppler. Run: doppler login"
  exit 1
fi

exec doppler run -- docker compose "$@"
