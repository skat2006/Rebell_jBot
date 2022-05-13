package org.example;

import java.util.Scanner;

public class CrossZero {

    private String[] cell;
    private int[] cellDigital;

    CrossZero() {
        cell = new String[] {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
        cellDigital = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    // Метод обработчик вывода на консоль, ячеек со строкового массива cell
    public String drawingOutput()
    {
        for (int i = 1; i < 10; i++) {
            if (cellDigital[i] == 1) {
                cell[i] = "X";
            } else if (cellDigital[i] == 5) {
                cell[i] = "0";
            } else {
                cell[i] = "*";
            }
        }

        String outPut =       cell[7] + " | " + cell[8] + " | " + cell[9] + "    7 | 8 | 9\n"
                            + cell[4] + " | " + cell[5] + " | " + cell[6] + "    4 | 5 | 6\n"
                            + cell[1] + " | " + cell[2] + " | " + cell[3] + "    1 | 2 | 3\n";

        return outPut;
    }

    private void temp() {
        Scanner enteredNumber = new Scanner(System.in);

    // Создаем матрицу (массив) из строк, он нам нужен для отображения знаков Х и 0, на консоле
    // Для удобного отображения на консоле пользователю (Играку)
    //String[] cell = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};

    // Создаем цифровую матрицу (массив) где  будут нули 0, будут О - цифрами 5, а Х - цифрами 1
    // именно с этой цифровой матрицей (массивом) и будут производится все действия и вычисления
    //int[] cellDigital = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    drawingOutput();// ВЫВОДИМ МАССИВ НА ЭКРАН КОНСОЛИ
    int position = integerInput();//ВВОДИМ НОМЕР ЯЧЕЙКИ КУДА БУДЕТ ЗАПИСЫВАТЬСЯ ЗНАЧЕНИЕ
    cell[position] = "X";
        System.out.println();
        System.out.println("Наблюдайте в какой клетке табилицы появится крест Х вместо нуля ");
    drawingOutput();// ВЫВОДИМ МАССИВ НА ЭКРАН КОНСОЛИ

    //Очищаем полностью от нулей строковый массив cell, для заполнения Х и 0 в игре консоли
        for (int m = 0; m < 10; m++) {
        cell[m] = " ";
    }
        System.out.print("  Player turn is - X , computer playing by - 0. \n " +
                "Who will start the game first? \n" +
                "For computer turn first, write - 2 ! \n " +
                "        Your turn first, write - 1 ! \n " +
                "                              write here ---> ");
    int turn = integerInputToo();//ВВОДИМ ЧИСЛО ОЧЕРЕДИ 1 - ЧЕЛОВЕК, 2 - ПРОГРАММА
    drawingOutput(); // ВЫВОДИМ ПОЛЕ ГДЕ БУДЕМ ИГРАТЬ (чистое)
        System.out.println("       ВНИМАНИЕ   ИГРА  НАЧИЛАСЬ  УДАЧИ ! ) ");
        System.out.println();
    int result = 24;// Значение говорит о том что Игра продолжается
    int more = 100; // Значение выхода из метода без результатно (метод не с работал)

    // ЦИКЛ САМОЙ ИГРЫ - НАЧАЛО
        do {

        if (turn == 1) {

            // ИГРОК ДЕЛАЕТ ХОД ВВОДИТ ЦИФРУ от 1 до 9 (вносятся изменения в цифровую матрицу (массив) sellDigital)
            cellDigital = gameMatrix(cellDigital);

            // ЦИФРОВАЯ МАТРИЦА (МАССИВ) sellDigital ПЕРЕВОДИТСЯ В СТОКОВУЮ (cell для отображения) И ВЫВОДИТСЯ В КОНСОЛЬ
            //drawingDigital(cellDigital, cell);

            // ПРОВЕРЯЕМ РЕЗУЛЬТАТ ИГРЫ (НИЧЬЯ 21,победил ИГРОК 22,победила ПРОГРАММА 23, ИГРА ДАЛЬШЕ 24)
            result = resultsGame(cellDigital);
            if (result == 21 || result == 22 || result == 23) {
                break;
            }

            // ХОД ПРОГРАММЫ
            System.out.println ("         ТЕПЕРЬ ХОЖУ Я (программа) :) ");
            more = winTableMove(cellDigital); //проверяем на выйгрыш, все строки возвращаем ход программы в переменную more
            if ( more == 100 ){
                more = PlayerDefenseMove (cellDigital); // проверяем на пройгрыш, все строки возвращаем ход программы в переменную more
            }
            if ( more == 100 ){
                more = numberFiveMove (cellDigital); // проверяем ячеку 5  возвращаем ход программы в переменную more
            }
            if ( more == 100 ){
                more = emptyCageMove (cellDigital); // ищем пустую ячеку для хода,возвращаем ход прогрммы в переменную more
            }
            cellDigital = programmMatrix(cellDigital, more ); // ход программы записывается в цифровую матрицу (массив)

            // ЦИФРОВАЯ МАТРИЦА (МАССИВ) sellDigital ПЕРЕВОДИТСЯ В СТОКОВУЮ cell И ВЫВОДИТСЯ В КОНСОЛЬ
            //drawingDigital(cellDigital, cell);

            // ПРОВЕРЯЕМ РЕЗУЛЬТАТ ИГРЫ (НИЧЬЯ 21,победил ИГРОК 22,победила ПРОГРАММА 23, ИГРА ДАЛЬШЕ 24)
            result = resultsGame(cellDigital);
            if (result == 21 || result == 22 || result == 23) {
                break;
            }

        } else if (turn == 2)
        {
            // ветка где первой ходит программа

            // ХОД ПРОГРАММЫ
            System.out.println ("         ТЕПЕРЬ ХОЖУ Я (программа) :) ");
            more = winTableMove(cellDigital); //проверяем на выйгрыш все строки возвращаем ход программы в переменную more
            if ( more == 100 ){
                more = PlayerDefenseMove (cellDigital); // проверяем на пройгрыш все строки возвращаем ход программы в переменную more
            }
            if ( more == 100 ){
                more = numberFiveMove (cellDigital); // проверяем ячеку 5  возвращаем ход программы в переменную more
            }
            if ( more == 100 ){
                more = emptyCageMove (cellDigital); // ищем пустую ячеку для хода  возвращаем ход программы в переменную more
            }
            cellDigital = programmMatrix(cellDigital, more ); // ход программы записывается в цифровую матрицу (массив)

            // ЦИФРОВАЯ МАТРИЦА (МАССИВ) sellDigital ПЕРЕВОДИТСЯ В СТОКОВУЮ cell И ВЫВОДИТСЯ В КОНСОЛЬ
            //drawingDigital(cellDigital, cell);

            // ПРОВЕРЯЕМ РЕЗУЛЬТАТ ИГРЫ (НИЧЬЯ 21,победил ИГРОК 22,победила ПРОГРАММА 23, ИГРА ДАЛЬШЕ 24)
            result = resultsGame(cellDigital);
            if (result == 21 || result == 22 || result == 23) {
                break;
            }
            // ИГРОК ДЕЛАЕТ ХОД ВВОДИТ ЦИФРУ от 1 до 9 (вносятся изменнеия в цифровую матрицу (массив) sellDigital
            cellDigital = gameMatrix(cellDigital);

            // ЦИФРОВАЯ МАТРИЦА (МАССИВ) sellDigital ПЕРЕВОДИТСЯ В СТОКОВУЮ cell И ВЫВОДИТСЯ В КОНСОЛЬ
            //drawingDigital(cellDigital, cell);

            // ПРОВЕРЯЕМ РЕЗУЛЬТАТ ИГРЫ (НИЧЬЯ 21,победил ИГРОК 22,победила ПРОГРАММА 23, ИГРА ДАЛЬШЕ 24)
            result = resultsGame(cellDigital);
            if (result == 21 || result == 22 || result == 23) {
                break;
            }
        }

    } while (result == 24);

    // ЗАКЛЮЧИТЕЛЬНАЯ ЧАСТЬ ПРОГРАММЫ КОТОРАЯ ПОДВОДИТ ИТОГ И ВЫВОДИТ РЕЗУЛЬТАТ В КОНСОЛЬ
        if (result == 21 ){
        System.out.println("         НИКТО НЕ ПОБЕДИЛ - У НАС НИЧЬЯ !");
        // ЦИФРОВАЯ МАТРИЦА (МАССИВ) sellDigital ПЕРЕВОДИТСЯ В СТОКОВУЮ cell И ВЫВОДИТСЯ В КОНСОЛЬ
        //drawingDigital(cellDigital, cell);
        System.out.println();
    } else if (result == 22 ) {
        System.out.println("          ПОЗДРАВЛЯЮ ВАС ВЫ ПОБЕДИТЕЛЬ !!!! ))) ");
        // ЦИФРОВАЯ МАТРИЦА (МАССИВ) sellDigital ПЕРЕВОДИТСЯ В СТОКОВУЮ cell И ВЫВОДИТСЯ В КОНСОЛЬ
        //drawingDigital(cellDigital, cell);
        System.out.println();
    } else {
        System.out.println("  К СОЖАЛЕНИЮ ВЫ ПРОИГРАЛИ, ВОЗМОЖНО ВАМ ПОВЕЗЁТ В СЛЕДУЮЩИЙ РАЗ!");
        // ЦИФРОВАЯ МАТРИЦА (МАССИВ) sellDigital ПЕРЕВОДИТСЯ В СТОКОВУЮ cell И ВЫВОДИТСЯ В КОНСОЛЬ
        //drawingDigital(cellDigital, cell);
        System.out.println();
    }
        System.out.println("                 ИГРА ОКОНЧЕНА !");
}


    //  МЕТОДЫ - ОБРАБОТЧИКИ

    // Метод обработчик ввода с клавиатуры числа,от 1 до 9, проверяет правильность ввода именно целого числа
    public static int integerInput()
    {
        Scanner enteredNumber = new Scanner(System.in);
        int keyboardNumber = 0;
        System.out.print("Введите число согласно позиции, куда Вы установите свой крестик Х, от 1 до 9, сюда --> ");
        do {
            if ((enteredNumber.hasNextInt())) {
                keyboardNumber = enteredNumber.nextInt();
                if (1 > keyboardNumber || keyboardNumber > 9) {
                    System.out.println("Вам нужно ввести число от 1 до 9 !!!  ");
                    keyboardNumber = 0;
                    System.out.print ("    вводите сюда ---> ");
                }
            } else {
                System.out.println("Вам нужно ввести число от 1 до 9, а не символ  !!! ");

                keyboardNumber = enteredNumber.nextInt();
            }
        } while (keyboardNumber == 0);
        return keyboardNumber;
    }


    // Метод обработчик ввода с клавиатуры, числа, проверяет правильность ввода именно целого числа 1 или 2
    public static int integerInputToo()
    {
        Scanner enteredNumber = new Scanner(System.in);
        int keyboardNumber = 0;
        do {
            if ((enteredNumber.hasNextInt())) {
                keyboardNumber = enteredNumber.nextInt();
                if (1 > keyboardNumber || keyboardNumber > 2) {
                    System.out.println("Вам нужно ввести число 1 или 2 !!!  ");
                    keyboardNumber = 0;
                    System.out.print ("    вводите сюда ---> ");
                }
            } else {
                System.out.println("Вам нужно ввести число 1 или 2, а не символ  !!! ");

                keyboardNumber = enteredNumber.nextInt();
            }
        } while (keyboardNumber == 0);
        return keyboardNumber;
    }

    //Метод обработки хода сделанного человеком
    public static int[] gameMatrix(int[] sellDigital)
    {
        int namber = integerInput();
        if (sellDigital[namber] == 0) { // условие если ячейка пуста (=0), то в неё записываем 1
            sellDigital[namber] = 1;
        } else { System.out.println ("        ЭТА КЛЕТКА ЗАНЯТА ВЫБЕРИТЕ ДРУГУЮ !!! Иначе программа будет играть без Вас! )");
            System.out.println();
            namber = integerInput(); }
        return sellDigital;
    }

    // Метод обработки результата хода игры возврат числа (продолжаем игру 24, победила программа 23, победил игрок 22, ничья 21)
    public static int resultsGame(int[] sellDigitals)
    {
        //Проверка всех линий по горизонтале по вертикале и по двум диагоналям
        //если все три ячейки вдоль линии заполнены 1 или 6 кто то выйграл (1-ИГРОК, 6-ПРОГРАММА)

        if (sellDigitals[7] + sellDigitals[8] + sellDigitals[9] == 3) {
            return 22;
        } else if (sellDigitals[7] + sellDigitals[8] + sellDigitals[9] == 15) {
            return 23;
        }
        if (sellDigitals[4] + sellDigitals[5] + sellDigitals[6] == 3) {
            return 22;
        } else if (sellDigitals[4] + sellDigitals[5] + sellDigitals[6] == 15) {
            return 23;
        }
        if (sellDigitals[1] + sellDigitals[2] + sellDigitals[3] == 3) {
            return 22;
        } else if (sellDigitals[1] + sellDigitals[2] + sellDigitals[3] == 15) {
            return 23;
        }
        if (sellDigitals[7] + sellDigitals[4] + sellDigitals[1] == 3) {
            return 22;
        } else if (sellDigitals[7] + sellDigitals[4] + sellDigitals[1] == 15) {
            return 23;
        }
        if (sellDigitals[8] + sellDigitals[5] + sellDigitals[2] == 3) {
            return 22;
        } else if (sellDigitals[8] + sellDigitals[5] + sellDigitals[2] == 15) {
            return 23;
        }
        if (sellDigitals[9] + sellDigitals[6] + sellDigitals[3] == 3) {
            return 22;
        } else if (sellDigitals[9] + sellDigitals[6] + sellDigitals[3] == 15) {
            return 23;
        }
        if (sellDigitals[7] + sellDigitals[5] + sellDigitals[3] == 3) {
            return 22;
        } else if (sellDigitals[7] + sellDigitals[5] + sellDigitals[3] == 15) {
            return 23;
        }
        if (sellDigitals[1] + sellDigitals[5] + sellDigitals[9] == 3) {
            return 22;
        } else if (sellDigitals[1] + sellDigitals[5] + sellDigitals[9] == 15) {
            return 23;
        }
        //Проверка всех членов массива, есть ли среди них пустые, если есть 24 играем дальше
        // а если нет то ходить некуда объявляем ничью и возвращаем 21
        /*for (int fox : sellDigitals) {
            if (fox == 0) {
                return 24;
            } */
        for ( int fox = 1; fox < 10; fox ++){
            if ( sellDigitals [fox] == 0) {
                return 24;
            }
        }
        return 21;//ничья
    }

    // Метод обработки поиска выйгрыша для Программы в цифровом массиве (матрицы) sellDigital,
    // то есть ищем два одинаковых значения 5, на одной линии, куда до этого, ходила программа
    // возвращаем целое число клетки (номер массива), пустой куда будет ходить программа что бы выйграть
    public static int winTableMove(int[] sellDigitals)
    {
        // Проверяем все линии в которых возможно стоят на одной линии два числа хода программы (цифра5)
        // и указываем выйгрышную третью позицию целым числом, куда и в дальнейшем и сходит программа
        return lineHandler(sellDigitals,10);
    }

    // Метод обработки защиты от выйграша Играком, проверяются все линии и если на одной из линий есть позиция
    // для выйграша играком, в эту клетку ходит программа и не дает игроку выйграть, цифры на линии единицы 1
    public static int PlayerDefenseMove(int[] sellDigitals)
    {
        // возвращаемое значение, это номер клетки куда программа поставит свой ход (5), что бы игрок не выйграл
        return lineHandler(sellDigitals,2);
    }

    // Метод обработчик линий применяется внутри других методов, для сокращения кода
    public static int  lineHandler (int[] sellDigitals, int values) {
        if (sellDigitals[7] + sellDigitals[8] + sellDigitals[9] == values) {
            if (sellDigitals[7] == 0) {
                return 7;
            } else if (sellDigitals[8] == 0) {
                return 8;
            } else {
                return 9;
            }
        } else if (sellDigitals[4] + sellDigitals[5] + sellDigitals[6] == values) {
            if (sellDigitals[4] == 0) {
                return 4;
            } else if (sellDigitals[5] == 0) {
                return 5;
            } else {
                return 6;
            }
        } else if (sellDigitals[1] + sellDigitals[2] + sellDigitals[3] == values) {
            if (sellDigitals[1] == 0) {
                return 1;
            } else if (sellDigitals[2] == 0) {
                return 2;
            } else {
                return 3;
            }
        } else if (sellDigitals[7] + sellDigitals[4] + sellDigitals[1] == values) {
            if (sellDigitals[7] == 0) {
                return 7;
            } else if (sellDigitals[4] == 0) {
                return 4;
            } else {
                return 1;
            }
        } else if (sellDigitals[8] + sellDigitals[5] + sellDigitals[2] == values) {
            if (sellDigitals[8] == 0) {
                return 8;
            } else if (sellDigitals[5] == 0) {
                return 5;
            } else {
                return 2;
            }
        } else if (sellDigitals[9] + sellDigitals[6] + sellDigitals[3] == values) {
            if (sellDigitals[9] == 0) {
                return 9;
            } else if (sellDigitals[6] == 0) {
                return 6;
            } else {
                return 3;
            }
        } else if (sellDigitals[7] + sellDigitals[5] + sellDigitals[3] == values) {
            if (sellDigitals[7] == 0) {
                return 7;
            } else if (sellDigitals[5] == 0) {
                return 5;
            } else {
                return 3;
            }
        } else if (sellDigitals[1] + sellDigitals[5] + sellDigitals[9] == values) {
            if (sellDigitals[1] == 0) {
                return 1;
            } else if (sellDigitals[5] == 0) {
                return 5;
            } else {
                return 9;
            }
        } else {
            return 100;//метод не отработал
        }
    }

    // Метод обработки числа 5ть куда ходить если оно не занято и что делать если оно занято программой или игроком
    public static int numberFiveMove (int[] sellDigitals)
    {
        if (sellDigitals[5] == 0) {
            return 5; // ячейка не занята будем делать в неё программа ход
        } else if (sellDigitals[5] == 1)
        {
            // ячейка занята Играком, ищем линии возможные для выйгрыша на которых заняты уже есть яцеки ходов программы
            if (sellDigitals[7] + sellDigitals[8] + sellDigitals[9] == 5) {
                if (sellDigitals[7] == 0) {
                    return 7;
                } else if (sellDigitals[8] == 0) {
                    return 8;
                } else {
                    return 9;
                }
            } else if (sellDigitals[4] + sellDigitals[5] + sellDigitals[6] == 5) {
                if (sellDigitals[4] == 0) {
                    return 4;
                } else if (sellDigitals[5] == 0) {
                    return 5;
                } else {
                    return 6;
                }
            } else if (sellDigitals[1] + sellDigitals[2] + sellDigitals[3] == 5) {
                if (sellDigitals[1] == 0) {
                    return 1;
                } else if (sellDigitals[2] == 0) {
                    return 2;
                } else {
                    return 3;
                }
            } else if (sellDigitals[7] + sellDigitals[4] + sellDigitals[1] == 5) {
                if (sellDigitals[7] == 0) {
                    return 7;
                } else if (sellDigitals[4] == 0) {
                    return 4;
                } else {
                    return 1;
                }
            } else if (sellDigitals[8] + sellDigitals[5] + sellDigitals[2] == 5) {
                if (sellDigitals[8] == 0) {
                    return 8;
                } else if (sellDigitals[5] == 0) {
                    return 5;
                } else {
                    return 2;
                }
            } else if (sellDigitals[9] + sellDigitals[6] + sellDigitals[3] == 5) {
                if (sellDigitals[9] == 0) {
                    return 9;
                } else if (sellDigitals[6] == 0) {
                    return 6;
                } else {
                    return 3;
                }
            } else if (sellDigitals[7] + sellDigitals[5] + sellDigitals[3] == 5) {
                if (sellDigitals[7] == 0) {
                    return 7;
                } else if (sellDigitals[5] == 0) {
                    return 5;
                } else {
                    return 3;
                }
            } else if (sellDigitals[1] + sellDigitals[5] + sellDigitals[9] == 5) {
                if (sellDigitals[1] == 0) {
                    return 1;
                } else if (sellDigitals[5] == 0) {
                    return 5;
                } else {
                    return 9;
                }
                // Свободных линий нет, проверяем угловые точки и занимаем одну из них
            } else if (sellDigitals[1] == 0) {
                return 1;
            } else if (sellDigitals[7] == 0) {
                return 7;
            } else if (sellDigitals[9] == 0) {
                return 9;
            } else if (sellDigitals[3] == 0) {
                return 3;
            }
        } else if (sellDigitals[5] == 5)// Ячека занята бывшим ходом программы
        {
            // Проверяем диагонали на выйграшные линии
            if (sellDigitals[1] + sellDigitals[5] + sellDigitals[9] == 5) {
                if (sellDigitals[1] == 0) {
                    return 1;
                } else if (sellDigitals[5] == 0) {
                    return 5;
                } else {
                    return 9;
                }

            } else if (sellDigitals[7] + sellDigitals[5] + sellDigitals[3] == 5) {
                if (sellDigitals[7] == 0) {
                    return 7;
                } else if (sellDigitals[5] == 0) {
                    return 5;
                } else {
                    return 3;
                }
            }
        } return 100;//метод не отработал
    }
    // Метод обработчик пустой клетки, ищет пустую клетку (цифра 0) в sellDigital, куда программа сделает свой ход
    public static int emptyCageMove (int[] sellDigitals)
    {
        for (int varible = 1; varible < 10; varible++ ){
            if ( sellDigitals[varible] == 0) { return varible ; }
        } return 100;//метод не отработал
    }

    // Метод установки программой хода в нужную ячейку цифрового массива (матрицы) sellDigital
    public static int[] programmMatrix(int[] sellDigital, int more)
    {

        if (sellDigital[more] == 0) { // условие если ячейка пуста (=0) то в нее записываем 1
            sellDigital[more] = 5;
        }
        return sellDigital;
    }
}
