package lotto.controller;

import camp.nextstep.edu.missionutils.Console;
import lotto.model.Game;
import lotto.model.Lotto;
import lotto.view.GameView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {
    Game game = new Game();
    Lotto lotto;

    public void startGame() {
        requestAmountInput();
        requestPurchaseCountOutput();
        requestLottoNumbers();
        requestWinningNumberInput();
        requestBonusNumberInput();
        requestMatchResultOutput();
        requestRevenueOutPut();
    }

    public void requestAmountInput() {
        GameView.printAmountInputPhrase();
        inputAmount();
    }
    public void inputAmount() {
        boolean validInput = false;
        int amount = 0;

        while (!validInput) {
            try {
                amount = Integer.parseInt(Console.readLine());
                checkAmountValid(amount);
                validInput = true; // 올바른 입력이 들어온 경우 반복 종료
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] 유효하지 않은 입력입니다. 다시 입력해주세요.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            game.setAmount(amount);
        }
    }

    private void checkAmountValid(int amount) {
        if (amount % 1000 != 0) {
            throw new IllegalArgumentException("[ERROR] 게임 금액은 1000의 배수여야 합니다.");
        }
    }

    public void requestPurchaseCountOutput() {
        GameView.printPurchaseCountOutputPhrase(game.getAmount() / game.getOneForPrice());
    }

    public void requestLottoNumbers() {
        game.generateLottoNumbers(game.getAmount() / game.getOneForPrice());
        for (List<Integer> numbers : game.getUserLottoNumbers()) {
            List<Integer> sortedUserLottoNumber = new ArrayList<>(numbers);
            Collections.sort(sortedUserLottoNumber);
            GameView.printLottoNumbers(sortedUserLottoNumber);
        }
    }

    public void requestBonusNumberInput() {
        GameView.printBonusNumberInputPhrase();
        inputBonusNumber();
    }

    public void requestRevenueOutPut() {
        GameView.printRevenue(game.getPrice(), game.getAmount());
    }

    public void requestMatchResultOutput() {
        List<Integer> matchList = game.getMatchResult(lotto.getNumbers());
        GameView.printMatchNumbers(matchList);
    }

    public void inputBonusNumber() {
        boolean validInput = false;
        while (!validInput) {
            try {
                int number = Integer.parseInt(Console.readLine());
                game.setBonusNumber(number);

                validInput = true;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("[ERROR] 유효하지 않은 입력입니다. 다시 입력해주세요.");
            }
        }
    }

    public void requestWinningNumberInput() {

        boolean validInput = false;

        while (!validInput) {
            try {
                GameView.printWinningNumberInputPhrase();
                lotto = new Lotto(StringToInteger(splitInput(Console.readLine(), ",")));

                validInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public List<Integer> StringToInteger(String[] input) {
        List<Integer> intList = new ArrayList<>();
        for (String str : input) {
            try {
                int number = Integer.parseInt(str);
                intList.add(number);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("[ERROR] 유효하지 않은 입력입니다. 다시 입력해주세요.");
            }
        }
        return intList;
    }

    public String[] splitInput(String input, String delimiter) {
        return input.split(delimiter);
    }
}