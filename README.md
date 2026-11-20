# Hamahang — هم‌آهنگ

نسخه آزمایشی آفلاین. داده‌ها فقط روی همان گوشی ذخیره می‌شوند.

## ورود آزمایشی

- شماره مدیر: `09156170125` (معادل `+989156170125`)
- کد OTP آزمایشی برای همه: `1234`
- PIN مدیر: `2580`
- کاربران دیگر: با هر شماره `09xxxxxxxxx` و OTP `1234` ثبت‌نام می‌کنند.

## ساخت APK با GitHub Actions (روش آسان، بدون نیاز به نصب اندروید استودیو)

1. محتویات این پوشه را داخل ریپازیتوری `hamahang` در گیت‌هاب آپلود/پوش کنید (برانچ `main`).
2. در گیت‌هاب به تب **Actions** بروید.
3. ورک‌فلو **Build Android APK** را انتخاب کنید.
4. دکمه **Run workflow** را بزنید و منتظر سبز شدن بیلد بمانید.
5. در صفحه همان اجرا، از بخش **Artifacts** فایل `hamahang-apk` را دانلود کنید.
6. ZIP را باز کنید و `app-debug.apk` را روی گوشی اندروید نصب کنید.
   اگر اندروید اجازه نداد، برای برنامه Files/مرورگر گزینه **Install unknown apps** را فعال کنید.

## اجرای محلی

```bash
./gradlew assembleDebug
# خروجی: app/build/outputs/apk/debug/app-debug.apk
```

## ساختار پروژه

```
settings.gradle
build.gradle
gradle.properties
app/build.gradle
app/src/main/AndroidManifest.xml
app/src/main/java/com/hamahang/app/MainActivity.kt
.github/workflows/build-apk.yml
```

## توجه

- OTP واقعی ارسال نمی‌شود، همیشه `1234` است.
- حساب‌ها بین گوشی‌ها مشترک نیستند (SharedPreferences محلی).
