# Wrapper Script for ChangeLog Verification
param (
  [String]$ChangeLogLocation,
  [String]$VersionString,
  [string]$PackageName,
  [string]$ArtifactName,
  [boolean]$ForRelease = $False
)

if (!$PackageName -and !$ArtifactName)
{
  LogError "You must specify either PackageName or ArtifactName Argument"
  return $null
}

. (Join-Path $PSScriptRoot common.ps1)

$validChangeLog = $false
if ($ChangeLogLocation -and $VersionString) 
{
  $validChangeLog = Confirm-ChangeLogEntry -ChangeLogLocation $ChangeLogLocation -VersionString $VersionString -ForRelease $ForRelease
}
else
{
  if ($PackageName)
  {
    $PackageProp = Get-PkgProperties -PackageName $PackageName
  }
  else
  {
    $PackageProp = Get-PkgProperties -ArtifactName $ArtifactName
  }

  if ($null -eq $PackageProp)
  {
    LogError "Failed to get Package Properties with arguments PackageName:$PackageName and ArtifactName:$ArtifactName ].`
              Ensure the correct PackageName or ArtifactName was specified."
  }
  $validChangeLog = Confirm-ChangeLogEntry -ChangeLogLocation $PackageProp.ChangeLogPath -VersionString $PackageProp.Version -ForRelease $ForRelease
}

if (!$validChangeLog)
{
  exit 1
}

exit 0