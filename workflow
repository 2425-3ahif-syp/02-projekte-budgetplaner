name: Asciidoc Slides CI

on:
  push:
    branches:
      - main
  pull_request:
    branches:
      - main

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Repository auschecken
        uses: actions/checkout@v4

      - name: Set up Asciidoctor
        run: sudo apt-get install -y asciidoctor

      - name: Slides generieren
        run: asciidoctor -r asciidoctor-revealjs -b revealjs slides.adoc
