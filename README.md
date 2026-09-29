# core-auth-ai-validation-framework
Test Validation Framework for core Auth AI solution 

## Branch and push rules

1. Commit and push your changes to your own branch at least every 30 minutes.
2. Merge your branch into `Develop` through a pull request.
3. Do not push to `main`. `main` is updated once a week by a pull request from `Develop`.
4. If your branch conflicts with `Develop`, contact Mr Dhan Singh before resolving it.

The repository's VS Code settings support these rules:

- Every commit made in VS Code is pushed immediately, pulling with rebase first. The status bar shows incoming and outgoing commits, refreshed every 5 minutes.
- The **Git sync reminder** task runs `scripts/git-sync-reminder.ps1` in the background when you open the folder. Every 30 minutes it shows a reminder when you have uncommitted or unpushed work, when your branch conflicts with `Develop`, or when you are on `main`. If your pushed branch has commits that are not in `Develop`, it offers to open a pull request. It never commits or pushes for you.
- On first start the reminder sets `core.hooksPath` to `.githooks`, whose `pre-push` hook rejects any push to `main`.

VS Code asks once whether to allow automatic tasks in this folder. Choose **Allow**, or run **Tasks: Manage Automatic Tasks** and allow them. Without VS Code, run `git config core.hooksPath .githooks` once and start the reminder with `powershell -STA -File scripts/git-sync-reminder.ps1`.
