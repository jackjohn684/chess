# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared tests`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
Here is the link for my Phase2 diagram:

https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpZnbvoccKQKoDOlF1hK9eBIiTAwA4sAC2KblV79GQliMQo8SNAHMZcnAKbCYAEWBhgAQRAgU7dnUwATE8ABGwTjHvO7djVAgBXbBgAYjRgKgBPGAAlFA0kdjAoEyQINBCAdwALJDA+RFRSAFoAPnJKGigALhgAbQAFAHkyABUAXRgAen9OKAAdNABvHsowqQAaGFwbDOh7SZQJYCQEAF9MGUoYUpgOLgreGqg4hLyoAAoRqDGUSen2Wah5mEXlhABKTD2obSttsuMpgsVhsNQ0KDA30uvRun0B5ks1nY-w8zhqZAAogAZDFwFowK43GAAMz8EgJvUw8OBSO2O2+vxQNTQ-gQCDoDIOfx21MRoJgIGOJhQUMJkluUzcDzmcKcNJsKM8NQAkgA5THRfFiiaSmZzBZLFYwNUtRoU2icnjcgFyvnsGqC1R5Mz+MBZaGjcWyoF2xVo43qjGa83XcWTYCurItCAAa3QAdNMAjbqptpByKK9N6jJqyajsfQX2zXJQdLKm2qScj0bjaA2FS2mbKBXQYBqACYAAydgaDPM19DrdD2Oi+AJBYLQFjgmBYiDxdLBbK5fLIVsois1BrNdpdTg2VJoXvaiX3R72dYV0qW2RWGoIefqD2hnVnmVF-ZWlClXnpsEQ0UYS9VMfXTUolXIbFcS1ICpBJMkQxAhEwOvYsvyqNAIBEFk2Q-H4Sx5NMkSqPNW0IYVnxucZ6ilc9vWQpFwLRTEcTxEMiRYexdWlJ54IgckriQ+V2CbG9DjdFAoiWMAQCyPDGR-IjQTgIVnUjSjgN-RiSiVNUNXxfsC3SE0zTzAk0HsLYTyEu1RLQ28mSrN0BzrMSrCbTdDNretqEoJsWwwKoux7IYvMHOhh1HPxAhCXwUHjOcNACEQlxyPJMACkQdk3OpDCgloMQ6Tp93YQ8BjCusrxKNymUS5Lzk+GrFNA4jDBQBAIRQF03QamywJ0tE8pxAqnPzWs+PJPM+sY1DPwcoLu3kgjSk3YKfMqfy10CtbIswMcYuCY4uKxU4xHFZFUpXDKtqy8sG0rWpRAxXdOg0cVyurIz1r8kpxCkHNkESP7rF64GFJKLTQWJaAXkqKI3qkKoBnBMBgfhXrIZEgaqhY6CYARlBlUMSZlzyb4qJgZwEFAGNybDAYkws-HxVVcUJuZqR4WmmwmzBksqgJomSbSkVYIlAYqZpumdU4jmUFZqRMD5r8PPugXxSFmBSdFz0dQl6mQFpsXwyZgmFb4K9SkyhaQsGQXia1kXpduBnJcN52Ta4s3xXWTBdv2idsH8KBsA6+BVNLYHMhF67Clu8pfIeppWiKgmPucoze29qRL3u0plfmlSnRQYHzmzlBPgL3hmoY0EzHsexgeVNBofOYHzfo4SmKqPSg3xNAUAyOWiZgdRIDlrmq-c7msd0wNg3L0eMAgCenCV8VwcxqpRHQShhVLzvbOxgN9JgAeh4Jpfx4JrnMd5jf+ft9f-uWkpNyfy2Smt4Ks41wwagAIztgAMwABYhwWSiuOEIER2oPiHgAKQgOoM6cFghuxjLHdc2U1Z1HqKwF6acwifVrL2TKcAIAPigJMe2udE75wfuhGASD1Cl3IZQ6ANC-6V0YQ5GuwkBYQlLvbQ+-UlS4zYvbdmN8153wYS-dCmFsKsnZFPb8EMlL2hRvUamsCoDsAxpo7uEj8Ta2dozew+spZi2keKW+Rjfq8MOC0LIpY0iljdCYGAAByZEEkYDsDZiADh-R0huHgDdChVDR7Ihwqopx7kNEtTrg3HRwA9HnHYVQrhUgiaiO0rpYkAoQkxICdgFARBiSoHsEjYy898TmLHivFAuRXGwDMWLAYMMMHmMsuUiy6gNAwDSDAKgwAED+BQAzCARSsmcIsUmBuMB-Gh3SVsce-iCYz27hiBAnAaj1y4is3RWxwnADKRU0wkAoDbPkdIfma01Gq0Tjbb6UBNpx1ef7aKE5fDpIiaoWAwBsCh3IgQEZl10qZQ3Lg2oQ1nqFS6J4N5VsOpqE0DmAUaKLifBUOirQBFqr2UOCAbF3wDGNWJYkre0NYAoDhiGWpHh2qdShJ8bZ2N4UjRPHcWicwFmvCNKSfiiE76cvyhiUaLkFknnZlNPFUBBngzUVUUlgLgYUufvclWSTa72hJDDellB4bilqZZDqeQD50DFUqLlkr7bCxXB7SmBsja6xdpoFmYYFmX2FeSNGsijGosBUqh5i0FWhpVitNWa1P7f0WpFIAA
