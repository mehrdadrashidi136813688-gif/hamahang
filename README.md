# Hamahang — Luxury UI update (Neshabur)

Android WebView app for wedding/event service discovery. This package includes the updated purple/gold interface and the signed-release GitHub Actions workflow.

## Version
- `versionCode`: 4
- `versionName`: 1.2 (kept unchanged)
- `applicationId`: `com.hamahang.app`

## UI changes
- 8-second luxury splash with skip option
- Purple/gold Home and service categories based on the approved design references
- Dance classes category for men, women, and couples
- Homa credits/rewards demo UI, locally stored only
- Shared logo artwork in header, Homa token display, and launcher icon
- Existing provider registration, event planner, profile, and demo admin sections retained

## Build
GitHub Actions workflow: `.github/workflows/blank.yml`.
Run **Build Hamahang Signed APK** and download the `hamahang-release-apk-v1.2` artifact.
The workflow uses the repository's configured GitHub Actions Secrets to sign the Release APK. No keystore or password is included in this archive.

## Prototype limitations
SMS login, backend/database, real payments, QR scanning, live booking/notifications, and actual Homa redemption are not connected. Demo profile/event/reward data is saved locally in the app.
