param(
    [string]$ContainerName = "comogastoapp-redis",
    [string]$Password = "comogasto-app-redis",
    [string]$Pattern = "*",
    [int]$MaxKeys = 50,
    [switch]$AsJson
)

$ErrorActionPreference = "Stop"

function Invoke-Redis {
    param([string[]]$Args)

    $result = docker exec -e REDISCLI_AUTH=$Password $ContainerName redis-cli @Args 2>$null
    if ($LASTEXITCODE -ne 0) {
        throw "Fallo al ejecutar redis-cli con argumentos: $($Args -join ' ')"
    }
    return ($result | Out-String).Trim()
}

function Get-LogicalSize {
    param(
        [string]$Key,
        [string]$Type
    )

    switch ($Type) {
        "string" { return [int](Invoke-Redis @("STRLEN", $Key)) }
        "hash"   { return [int](Invoke-Redis @("HLEN", $Key)) }
        "list"   { return [int](Invoke-Redis @("LLEN", $Key)) }
        "set"    { return [int](Invoke-Redis @("SCARD", $Key)) }
        "zset"   { return [int](Invoke-Redis @("ZCARD", $Key)) }
        "stream" { return [int](Invoke-Redis @("XLEN", $Key)) }
        default   { return $null }
    }
}

$running = (docker inspect -f "{{.State.Running}}" $ContainerName 2>$null | Out-String).Trim()
if ($running -ne "true") {
    throw "El contenedor '$ContainerName' no esta corriendo."
}

$dbSize = [int](Invoke-Redis @("DBSIZE"))
$keyspace = Invoke-Redis @("INFO", "keyspace")

Write-Host "Redis audit" -ForegroundColor Cyan
Write-Host "Container : $ContainerName"
Write-Host "Pattern   : $Pattern"
Write-Host "DBSIZE    : $dbSize"
Write-Host ""
Write-Host "Keyspace:" -ForegroundColor Yellow
Write-Host $keyspace
Write-Host ""

$keys = docker exec -e REDISCLI_AUTH=$Password $ContainerName redis-cli --scan --pattern $Pattern 2>$null |
    Select-Object -First $MaxKeys

if (-not $keys) {
    Write-Host "No se encontraron claves con el patron '$Pattern'." -ForegroundColor Green
    exit 0
}

$rows = foreach ($key in $keys) {
    $type = Invoke-Redis @("TYPE", $key)
    $ttl = [int](Invoke-Redis @("TTL", $key))
    $logicalSize = Get-LogicalSize -Key $key -Type $type

    # Preview breve para strings para facilitar debug rapido.
    $preview = $null
    if ($type -eq "string") {
        $preview = Invoke-Redis @("GETRANGE", $key, "0", "100")
    }

    [PSCustomObject]@{
        Key         = $key
        Type        = $type
        TTLSeconds  = $ttl
        LogicalSize = $logicalSize
        Preview     = $preview
    }
}

if ($AsJson) {
    $rows | ConvertTo-Json -Depth 5
} else {
    $rows | Format-Table -AutoSize
}

