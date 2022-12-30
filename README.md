# 株式会社ゆめみ Android エンジニアコードチェック課題

## 概要

本プロジェクトは株式会社ゆめみ（以下弊社）が、弊社に Android エンジニアを希望する方に出された課題のベースプロジェクトです。

本家プロジェクトは、[こちら](https://github.com/yumemi-inc/android-engineer-codecheck)

## アプリ仕様

本アプリは GitHub のリポジトリを検索するアプリです。

<img src="docs/app.gif" width="320">

### 環境

本家プロジェクトで指定された環境に合わせています。

- IDE：Android Studio Arctic Fox | 2020.3.1 Patch 1
  - [こちらのアーカイブ](https://developer.android.com/studio/archive)からインストールします
- Kotlin：1.5.31
- Java：11
- Gradle：7.0.1
- minSdk：23
- targetSdk：31

### 動作

1. アプリを起動する
2. 検索バーに適当なキーワードを入力する
3. GitHub API（`search/repositories`）でリポジトリを検索し、結果一覧を概要（リポジトリ名）で表示する
4. 特定の結果を選択したら、該当リポジトリの詳細（リポジトリ名、オーナーアイコン、プロジェクト言語、Star 数、Watcher 数、Fork 数、Issue 数）を表示する

##　提出方法

本プロジェクトのリポジトリを public にした状態で、URL を共有する。

## 参考記事

- [ゆめみの Android の採用コーディング試験を公開しました](https://qiita.com/blendthink/items/aa70b8b3106fb4e3555f)
