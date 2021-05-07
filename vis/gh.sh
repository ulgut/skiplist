base_dir=/Dev/School/Semesters/2020-2021_S2/CSCI1102/psets/ps9-jessetuglu

build(){
  cd "$base_dir"/vis
  npm run build
}

subtree(){
  cd "$base_dir"/vis
  git add build/ && git commit -m "new build"
  cd ..
  git subtree pull --prefix vis/build personal gh-pages
  git subtree push --prefix vis/build personal gh-pages
}

build
wait
subtree