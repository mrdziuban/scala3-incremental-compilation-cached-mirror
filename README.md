# Scala 3 `Mirror` cached on incremental compilation

This repository demonstrates an issue where Scala 3 `Mirror`s are cached on incremental compilation and report the
incorrect set of `MirroredElemLabels` and `MirroredElemTypes` for a type that's been changed.

To reproduce the issue:

1. Clone this repo
2. `cd` into it
3. Run `./test.sh`

You'll see output like this:

```
Running with fields: `str: String`

***** Fields: test(str)


Running with fields: `str: String, int: Int`

***** Fields: test(str)
```

The second `***** Fields` line *should* say `***** Fields: test(str, int)`, but the `Mirror` still reported that
`MirroredElemLabels` was just `(str)`.

## Reproduce manually

The following are the steps that `test.sh` takes so you can reproduce manually if you'd like to:

1. Ensure `case class Test` in [Test.scala](src/main/scala/example/Test.scala) looks like this:
    ```scala
    case class Test(str: String)
    ```
2. Run `sbt run`
3. Modify `case class Test` to look like this:
    ```scala
    case class Test(str: String, int: Int)
    ```
4. Run `sbt run`
