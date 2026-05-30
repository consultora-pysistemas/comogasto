#!/usr/bin/env bash
set -euo pipefail

# Validate Spring Boot AutoConfiguration.imports consistency for all starters.
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
STARTERS_DIR="$ROOT_DIR/backend/starters"

if [[ ! -d "$STARTERS_DIR" ]]; then
  echo "ERROR: Starters directory not found: $STARTERS_DIR" >&2
  exit 2
fi

fail_count=0

for starter_dir in "$STARTERS_DIR"/*; do
  [[ -d "$starter_dir" ]] || continue
  starter_name="$(basename "$starter_dir")"

  imports_file="$starter_dir/src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports"
  java_root="$starter_dir/src/main/java"

  echo "--- $starter_name ---"

  if [[ ! -f "$imports_file" ]]; then
    echo "[FAIL] Missing imports file: $imports_file"
    ((fail_count+=1))
    continue
  fi

  bad_imports_in_java="$(find "$java_root" -type f -name 'org.springframework.boot.autoconfigure.AutoConfiguration.imports' -print -quit 2>/dev/null || true)"
  if [[ -n "$bad_imports_in_java" ]]; then
    echo "[FAIL] Imports file in wrong location: $bad_imports_in_java"
    ((fail_count+=1))
    continue
  fi

  # Ignore blank/comment lines and take first valid FQCN.
  imports_fqcn="$(grep -E '^[[:space:]]*[^#[:space:]].*' "$imports_file" | head -n 1 | tr -d '\r' | xargs || true)"
  if [[ -z "$imports_fqcn" ]]; then
    echo "[FAIL] Empty or invalid imports file: $imports_file"
    ((fail_count+=1))
    continue
  fi

  mapfile -t auto_configs < <(find "$java_root" -type f -name '*AutoConfiguration.java' 2>/dev/null)
  if [[ "${#auto_configs[@]}" -ne 1 ]]; then
    echo "[FAIL] Expected exactly one *AutoConfiguration.java, found ${#auto_configs[@]}"
    ((fail_count+=1))
    continue
  fi

  java_file="${auto_configs[0]}"
  package_name="$(awk '/^package[[:space:]]+/{gsub(/\r/, ""); sub(/^package[[:space:]]+/, ""); sub(/;$/, ""); print; exit}' "$java_file")"
  class_name="$(basename "$java_file" .java)"
  expected_fqcn="$package_name.$class_name"

  if [[ "$imports_fqcn" != "$expected_fqcn" ]]; then
    echo "[FAIL] FQCN mismatch"
    echo "       imports : $imports_fqcn"
    echo "       expected: $expected_fqcn"
    ((fail_count+=1))
    continue
  fi

  echo "[OK] $expected_fqcn"
done

if [[ "$fail_count" -gt 0 ]]; then
  echo
  echo "Validation finished with $fail_count failure(s)." >&2
  exit 1
fi

echo
echo "Validation finished successfully."

