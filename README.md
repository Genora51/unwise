# Unwise

An intentionally insecure web application.
There are three flags hidden in the application;
each one can be found by exploiting a different vulnerability.

## Challenge 1 - Easy

If you can get your account balance to be over £1 billion,
the first flag will be displayed. Can you exploit UnWise's
security failings and make yourself a billionaire?

## Challenge 2 - Medium

The second flag can be found in a transaction sent from `Kristo` to `DefinitelyNotKristo`.
Looks like someone is trying to hide something from the taxman...

Can you find a way to view the secret transaction and claim the flag
before HMRC gets there first?

## Challenge 3 - L33t H4x0r

Paranoid about the security of his new system, and not having written any unit tests,
Kristo has resorted to manually testing his code, frantically logging in and out,
sending transactions, and checking his balance. Can you leak Kristo's account
password and claim the final flag?

## Notes

You can run the application locally by running `./gradlew bootRun` in the root directory.
The application will be available at `http://localhost:8080`.
You can find the fake flags for local testing in `application.properties`.