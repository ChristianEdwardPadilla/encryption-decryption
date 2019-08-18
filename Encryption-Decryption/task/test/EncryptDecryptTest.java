import encryptdecrypt.Main;
import org.hyperskill.hstest.v5.common.Utils;
import org.hyperskill.hstest.v5.stage.BaseStageTest;
import org.hyperskill.hstest.v5.testcase.CheckResult;
import org.hyperskill.hstest.v5.testcase.TestCase;

import java.util.List;

public class EncryptDecryptTest extends BaseStageTest<String> {
    public EncryptDecryptTest() throws Exception {
        super(Main.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return List.of(
            new TestCase<String>()
                .addArguments(new String[]{
                    "-mode", "enc",
                    "-key", "5",
                    "-in", "in.txt",
                    "-out", "output.txt"
                })
                    .addFile("in.txt", "Welcome to hyperskill!")
                .setAttach("\\jqhtrj%yt%m~ujwxpnqq&"),

            new TestCase<String>()
                .addArguments(new String[]{
                    "-key", "0",
                    "-mode", "enc",
                    "-in", "input.txt",
                    "-out", "output.txt"
                })
                .addFile("input.txt", "Hello")
                    .setAttach("Hello"),

            new TestCase<String>()
                .addArguments(new String[]{
                    "-key", "1",
                    "-in", "i.txt",
                    "-out", "output.txt",
                    "-mode", "enc"
                })
                .addFile("i.txt", "012345678")
                    .setAttach("123456789"),

            new TestCase<String>()
                .addArguments(new String[]{
                    "-mode", "dec",
                    "-in", "input.txt",
                    "-out", "output.txt",
                    "-key", "5"
                })
                .addFile("input.txt", "\\jqhtrj%yt%m~ujwxpnqq&")
                    .setAttach("Welcome to hyperskill!"),

            new TestCase<String>()
                .addArguments(new String[]{
                    "-mode", "dec",
                    "-key", "0",
                    "-in", "input.txt",
                    "-out", "output.txt"
                })
                .addFile("input.txt", "Hi")
                    .setAttach("Hi"),

            new TestCase<String>()
                .addArguments(new String[]{
                    "-mode", "dec",
                    "-key", "1",
                    "-in", "input.txt",
                    "-out", "output.txt"
                })
                    .addFile("input.txt", "222233334444")
                    .setAttach("111122223333")
        );
    }

    @Override
    public CheckResult check(String reply, String clue) {
        String output = Utils.readFile("output.txt");
        return new CheckResult(output.trim().equals(clue.trim()));
    }
}
