build() {
  cd vis
  npm run build
  cd ..
  rm -rf docs/*
  cp -r vis/build/* docs/
}
build
