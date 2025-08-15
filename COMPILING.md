Компіляція
=========

Ви можете скомпілювати WorldGuard, якщо у вас встановлена версія Java, що дорівнює або перевищує 21.
Gradle завантажить JDK 21, якщо це буде потрібно, але для запуску йому потрібна якась версія Java.

Процес побудови використовує Gradle, який вам *не* потрібно завантажувати. WorldGuard — це багатомодульний проект, що складається з трьох модулів:

* `worldguard-core` містить WorldGuard API
* `worldguard-bukkit` це Bukkit плагін
* `worldguard-libs` містить перевизначення бібліотек

## Для компілювання...

### На Windows

1. **Shift** + **правий клік** папку з файлами WorldGuard і натисніть "Відкрити вікно PowerShell тут".
2. `gradlew build`

### На Linux, BSD, або Mac OS X

1. У терміналі перейдіть до директорії з файлами WorldGuard (`cd /folder/of/worldguard/files`)
2. `./gradlew build`

## Тоді ви знайдете...

Ви знайдете:

* Ядро WorldGuard API в **worldguard-core/build/libs**
* WorldGuard для Bukkit в **worldguard-bukkit/build/libs**

Якщо ви хочете використовувати WorldGuard, використовуйте версію `-dist`.

(Версія -dist включає WorldGuard + необхідні бібліотеки.)

## Інші команди

* `gradlew idea` створить [IntelliJ IDEA](http://www.jetbrains.com/idea/) модуль для кожної папки.
* `gradlew eclipse` створить [Eclipse](https://www.eclipse.org/downloads/) проєкт для кожної папки.
