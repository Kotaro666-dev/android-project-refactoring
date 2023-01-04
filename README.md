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

### 採用アーキテクチャ

MVVM + UseCase + Repository パターンを導入しました。
このアーキテクチャの導入を実現するために、[Dagger Hilt](https://dagger.dev/hilt/) ライブラリによる依存性注入を使用しています。

参考資料: [Hilt を使用した依存関係の注入](https://developer.android.com/training/dependency-injection/hilt-android)

### プロジェクト内のディレクトリ構造

- data
    - api
        - 外部ネットワークとのAPI処理を担当します
        - 今回のプロジェクトでは、GithubSearchAPI との処理を行っています
    - repository
        - ネットワークやDB、ファイルIOなどのデータソースへのアクセス処理を担当します
        - 今回のプロジェクトでは、アプリ層からのネットワーク通信処理の依頼を対応しています
- di
    - Dagger Hilt での依存性注入処理に必要な対応をしています
    - 今回のプロジェクトでは、Class と Interface の紐付け処理を行っています
- model
    - データを表現するオブジェクトクラスの管理を担当します
    - 今回のプロジェクトでは、GithubRepositoryData クラスを管理しています
- ui
    - アプリデータの表示やユーザー操作によるイベント対応や変更処理を担当します
    - データの保持と処理は、ViewModel が対応します
- usecase
    - ui 層と data 層の間で、複雑なビジネスロジック処理を担当します
    - 今回のプロジェクトでは、data 層から取得したネットワーク通信結果を変換する作業を行っています
- utilities
    - 補助的な機能処理を担当します
    - 今回のプロジェクトでは、RecyclerView で必要な Adapter クラスの管理をしています

## 　提出方法

本プロジェクトのリポジトリを public にした状態で、URL を共有する。

## 参考記事

- [ゆめみの Android の採用コーディング試験を公開しました](https://qiita.com/blendthink/items/aa70b8b3106fb4e3555f)
