build() {
  cd vis
  npm run build
  cd ..
  cp -r vis/build/* docs/
}
build
