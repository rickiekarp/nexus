# README #

Merging a git submodule into the main repo

### How To ###

--- Deinit and remove submodule
```
git submodule deinit <path_to_submodule>
git rm <path_to_submodule>
git commit-m "Removed submodule <name>"
rm -rf .git/modules/<path_to_submodule>
git push
```

--- Add upstream remote for module to merge
git remote add origin-to-merge git@<remote>.git

--- Merge upstream repo
git merge origin-to-merge/master master --allow-unrelated-histories
