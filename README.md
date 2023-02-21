# GradleVersionCompanion

The gradle version companion plugin automatically detects the current version of the project based on git tags.
The plugin also provides helper tasks for printing the detected version and a list of all detected versions.

## Usage

The project version is automatically detected and set by the plugin.

| Commit | Tags | Version |
| --- | --- | --- |
| 1 | | 0.1.0-dev001 |
| 2 | v0.1.0 | 0.1.0 |
| 3 | | 0.1.1-dev001 |
| 4 | v0.1.1 | 0.1.1 |
| 5 | | 0.1.2-dev001 |
| 6 | v0.2.x | 0.2.0-dev002 |
| 7 | | 0.2.0-dev003 |
| 8 | v0.2.0-rc.1 | 0.2.0-rc.1 |
| 9 | | 0.2.0-dev001 |
| 10 | v0.2.0 | 0.2.0 |

To see the current version use:
```
gradlew currentVersion
```

To see a list of all versions in the repository use:
```
gradlew allVersions
```

## Tags

All version tags must use the same prefix and suffix specified by the configuration.
The versions can then be either a full version or a short one.
A full version is a valid [semantic version](https://semver.org/) with all three version numbers present.
A short version is the same as a full version except that the patch number is omitted or replaced by an `x`.

Full versions represent releases including prereleases.
Short versions represent the first commit of a new major or minor version.

If the current commit does not contain a full version tag, then the current version will be a development version.
When the current version is a development version, `dev` is appended to the release info.
Development versions represent a prerelease version of the next full version.
The next full version is based on the latest previous version tag.

## Configuration

All settings and their default values:

```
versionCompanion {
  
  tagPrefix = "v"
  tagSuffix = ""
  
  useDirty = true
  useDepth = true
  useTimestamp = false
  
  timestampFormat = "yyMMdd.HHmm"
  
}
```

The use of each setting:
| Name | Description |
| ---- | ----------- |
| tagPrefix | Ignore tags that don't start with this string. The plugin removes the prefix before parsing the version. |
| tagSuffix | Ignore tags that don't end with this string. The plugin removes the suffix before parsing the version. |
| useDirty | If uncommitted changes exist, append "dirty" to the release info. Ex. `1.2.3-dirty`. |
| useDepth | If the current version is a development version, add the number of commits since the last version tag. Ex. `1.2.3-dev037`. |
| useTimestamp | If the current version is a development version, append a timestamp to the build info. Ex. `1.2.3-dev+230217.2158`. |
| timestampFormat | The format to use for the timestamp using the `DateTimeFormatter.ofPattern()` method syntax. |
