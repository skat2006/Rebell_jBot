package org.example;

public class CrossZero {

    private final String[] cell;
    private int[] cellDigital;
    private String gameRes;
    private boolean gameOngoing;
    private long userID = -1;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName = null;

    CrossZero() {
        cell = new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
        cellDigital = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gameRes = " ";
    }

    public void setGameOngoing(boolean gameOngoing) {
        this.gameOngoing = gameOngoing;
    }

    public boolean isGameOngoing() {
        return this.gameOngoing;
    }

    public long getUserID() {
        return this.userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String drawingOutput() {
        for (int i = 1; i < 10; i++) {
            if (cellDigital[i] == 1) {
                cell[i] = "X";
            } else if (cellDigital[i] == 5) {
                cell[i] = "0";
            } else {
                cell[i] = "*";
            }
        }

        String outPut;
        outPut = cell[7] + " | " + cell[8] + " | " + cell[9] + "    7 | 8 | 9\n"
                + cell[4] + " | " + cell[5] + " | " + cell[6] + "    4 | 5 | 6\n"
                + cell[1] + " | " + cell[2] + " | " + cell[3] + "    1 | 2 | 3\n";
        outPut += "\n" + gameRes;
        return outPut;
    }

    public void gameOngoing(int index) {
        int more = 100; // Значение выхода из метода без результатно (метод не с работал)

        // ИГРОК ДЕЛАЕТ ХОД
        if (cellDigital[index] == 0) {
            cellDigital[index] = 1;
            gameRes = "Ваш ход...\n";
        } else {
            gameRes = "Клетка занята, сделайте другой ход...";
            gameOngoing = true;
            return;
        }

        // КОМПЬЮТЕР ДЕЛАЕТ ХОД
        if (checkResult() == 24) {
            do {
                more = winTableMove(cellDigital); //проверяем на выйгрыш, все строки возвращаем ход программы в переменную more
                if (more == 100) {
                    more = PlayerDefenseMove(cellDigital); // проверяем на пройгрыш, все строки возвращаем ход программы в переменную more
                }
                if (more == 100) {
                    more = numberFiveMove(cellDigital); // проверяем ячеку 5  возвращаем ход программы в переменную more
                }
                if (more == 100) {
                    more = emptyCageMove(cellDigital); // ищем пустую ячеку для хода, возвращаем ход прогрммы в переменную more
                }
            } while (more == 100);
        }
        cellDigital = pMatrix(cellDigital, more); // ход программы записывается в цифровую матрицу (массив)

        // ПРОВЕРЯЕМ РЕЗУЛЬТАТ ИГРЫ
        switch (checkResult()) {
            case 21 -> {
                gameRes = "А вот и НИЧЬЯ! :)";
                gameOngoing = false; // игра окончена
                if (userID != -1) {
                    JDBSpostgreSQL jdbSpostgreSQL = new JDBSpostgreSQL();
                    jdbSpostgreSQL.insertUserRecord(userID, userName);
                }
                return;
            }
            case 22 -> {
                gameRes = "ВЫ ПОБЕДИТЕЛЬ !!!\nПриклоняюсь перед Вашим мастерством!";
                gameOngoing = false; // игра окончена
                if (userID != -1) {
                    JDBSpostgreSQL jdbSpostgreSQL = new JDBSpostgreSQL();
                    jdbSpostgreSQL.increaseScoreByID(userID, userName);
                }
                return;
            }
            case 23 -> {
                gameRes = "ВЫ ПРОИГРАЛИ!\nСыграем еще разок?";
                gameOngoing = false; // игра окончена
                if (userID != -1) {
                    JDBSpostgreSQL jdbSpostgreSQL = new JDBSpostgreSQL();
                    jdbSpostgreSQL.insertUserRecord(userID, userName);
                }
                return;
            }
        }
        // продолжаем игру
        gameOngoing = true;
    }

    // Метод обработки результата хода игры возврат числа (продолжаем игру 24, победила программа 23, победил игрок 22, ничья 21)
    public int checkResult() {
        //Проверка всех линий по горизонтале по вертикале и по двум диагоналям
        if ((cellDigital[7] + cellDigital[8] + cellDigital[9]) == 3
                || (cellDigital[4] + cellDigital[5] + cellDigital[6]) == 3
                || (cellDigital[1] + cellDigital[2] + cellDigital[3]) == 3
                || (cellDigital[7] + cellDigital[4] + cellDigital[1]) == 3
                || (cellDigital[8] + cellDigital[5] + cellDigital[2]) == 3
                || (cellDigital[9] + cellDigital[6] + cellDigital[3]) == 3
                || (cellDigital[7] + cellDigital[5] + cellDigital[3]) == 3
                || (cellDigital[1] + cellDigital[5] + cellDigital[9]) == 3) {
            return 22;
        } else if ((cellDigital[7] + cellDigital[8] + cellDigital[9]) == 15
                || (cellDigital[4] + cellDigital[5] + cellDigital[6]) == 15
                || (cellDigital[1] + cellDigital[2] + cellDigital[3]) == 15
                || (cellDigital[7] + cellDigital[4] + cellDigital[1]) == 15
                || (cellDigital[8] + cellDigital[5] + cellDigital[2]) == 15
                || (cellDigital[9] + cellDigital[6] + cellDigital[3]) == 15
                || (cellDigital[7] + cellDigital[5] + cellDigital[3]) == 15
                || (cellDigital[1] + cellDigital[5] + cellDigital[9]) == 15) {
            return 23;
        } else {
            //Проверка всех членов массива, есть ли среди них пустые, если есть 24 играем дальше
            // а если нет то ходить некуда объявляем ничью и возвращаем 21
            for (int fox = 1; fox < 10; fox++) {
                if (cellDigital[fox] == 0) {
                    return 24;
                }
            }
            return 21; //ничья
        }
    }

    // Метод обработки поиска выйгрыша для Программы в цифровом массиве (матрицы) sellDigital,
    // то есть ищем два одинаковых значения 5, на одной линии, куда до этого, ходила программа
    // возвращаем целое число клетки (номер массива), пустой куда будет ходить программа что бы выйграть
    public static int winTableMove(int[] sellDigital) {
        // Проверяем все линии в которых возможно стоят на одной линии два числа хода программы (цифра5)
        // и указываем выйгрышную третью позицию целым числом, куда и в дальнейшем и сходит программа
        return lineHandler(sellDigital, 10);
    }

    // Метод обработки защиты от выйграша Играком, проверяются все линии и если на одной из линий есть позиция
    // для выйграша играком, в эту клетку ходит программа и не дает игроку выйграть, цифры на линии единицы 1
    public static int PlayerDefenseMove(int[] sellDigital) {
        // возвращаемое значение, это номер клетки куда программа поставит свой ход (5), что бы игрок не выйграл
        return lineHandler(sellDigital, 2);
    }

    // Метод обработчик линий применяется внутри других методов, для сокращения кода
    public static int lineHandler(int[] sellDigital, int values) {
        if (sellDigital[7] + sellDigital[8] + sellDigital[9] == values) {
            if (sellDigital[7] == 0) {
                return 7;
            } else if (sellDigital[8] == 0) {
                return 8;
            } else {
                return 9;
            }
        } else if (sellDigital[4] + sellDigital[5] + sellDigital[6] == values) {
            if (sellDigital[4] == 0) {
                return 4;
            } else if (sellDigital[5] == 0) {
                return 5;
            } else {
                return 6;
            }
        } else if (sellDigital[1] + sellDigital[2] + sellDigital[3] == values) {
            if (sellDigital[1] == 0) {
                return 1;
            } else if (sellDigital[2] == 0) {
                return 2;
            } else {
                return 3;
            }
        } else if (sellDigital[7] + sellDigital[4] + sellDigital[1] == values) {
            if (sellDigital[7] == 0) {
                return 7;
            } else if (sellDigital[4] == 0) {
                return 4;
            } else {
                return 1;
            }
        } else if (sellDigital[8] + sellDigital[5] + sellDigital[2] == values) {
            if (sellDigital[8] == 0) {
                return 8;
            } else if (sellDigital[5] == 0) {
                return 5;
            } else {
                return 2;
            }
        } else if (sellDigital[9] + sellDigital[6] + sellDigital[3] == values) {
            if (sellDigital[9] == 0) {
                return 9;
            } else if (sellDigital[6] == 0) {
                return 6;
            } else {
                return 3;
            }
        } else if (sellDigital[7] + sellDigital[5] + sellDigital[3] == values) {
            if (sellDigital[7] == 0) {
                return 7;
            } else if (sellDigital[5] == 0) {
                return 5;
            } else {
                return 3;
            }
        } else if (sellDigital[1] + sellDigital[5] + sellDigital[9] == values) {
            if (sellDigital[1] == 0) {
                return 1;
            } else if (sellDigital[5] == 0) {
                return 5;
            } else {
                return 9;
            }
        } else {
            return 100;//метод не отработал
        }
    }

    // Метод обработки числа 5ть куда ходить если оно не занято и что делать если оно занято программой или игроком
    public static int numberFiveMove(int[] sellDigital) {
        if (sellDigital[5] == 0) {
            return 5; // ячейка не занята, сходим в нее
        } else if (sellDigital[5] == 1) {
            // ячейка занята Игроком, ищем линии возможные для выйгрыша
            if (sellDigital[7] + sellDigital[8] + sellDigital[9] == 5) {
                if (sellDigital[7] == 0) {
                    return 7;
                } else if (sellDigital[8] == 0) {
                    return 8;
                } else {
                    return 9;
                }
            } else if (sellDigital[4] + sellDigital[5] + sellDigital[6] == 5) {
                if (sellDigital[4] == 0) {
                    return 4;
                } else if (sellDigital[5] == 0) {
                    return 5;
                } else {
                    return 6;
                }
            } else if (sellDigital[1] + sellDigital[2] + sellDigital[3] == 5) {
                if (sellDigital[1] == 0) {
                    return 1;
                } else if (sellDigital[2] == 0) {
                    return 2;
                } else {
                    return 3;
                }
            } else if (sellDigital[7] + sellDigital[4] + sellDigital[1] == 5) {
                if (sellDigital[7] == 0) {
                    return 7;
                } else if (sellDigital[4] == 0) {
                    return 4;
                } else {
                    return 1;
                }
            } else if (sellDigital[8] + sellDigital[5] + sellDigital[2] == 5) {
                if (sellDigital[8] == 0) {
                    return 8;
                } else if (sellDigital[5] == 0) {
                    return 5;
                } else {
                    return 2;
                }
            } else if (sellDigital[9] + sellDigital[6] + sellDigital[3] == 5) {
                if (sellDigital[9] == 0) {
                    return 9;
                } else if (sellDigital[6] == 0) {
                    return 6;
                } else {
                    return 3;
                }
            } else if (sellDigital[7] + sellDigital[5] + sellDigital[3] == 5) {
                if (sellDigital[7] == 0) {
                    return 7;
                } else if (sellDigital[5] == 0) {
                    return 5;
                } else {
                    return 3;
                }
            } else if (sellDigital[1] + sellDigital[5] + sellDigital[9] == 5) {
                if (sellDigital[1] == 0) {
                    return 1;
                } else if (sellDigital[5] == 0) {
                    return 5;
                } else {
                    return 9;
                }
                // Свободных линий нет, проверяем угловые точки и занимаем одну из них
            } else if (sellDigital[1] == 0) {
                return 1;
            } else if (sellDigital[7] == 0) {
                return 7;
            } else if (sellDigital[9] == 0) {
                return 9;
            } else if (sellDigital[3] == 0) {
                return 3;
            }
        } else if (sellDigital[5] == 5)// Ячека занята бывшим ходом программы
        {
            // Проверяем диагонали на выйграшные линии
            if (sellDigital[1] + sellDigital[5] + sellDigital[9] == 5) {
                if (sellDigital[1] == 0) {
                    return 1;
                } else if (sellDigital[5] == 0) {
                    return 5;
                } else {
                    return 9;
                }

            } else if (sellDigital[7] + sellDigital[5] + sellDigital[3] == 5) {
                if (sellDigital[7] == 0) {
                    return 7;
                } else if (sellDigital[5] == 0) {
                    return 5;
                } else {
                    return 3;
                }
            }
        }
        return 100;//метод не отработал
    }

    // Метод обработчик пустой клетки, ищет пустую клетку (цифра 0) в sellDigital, куда программа сделает свой ход
    public static int emptyCageMove(int[] sellDigital) {
        for (int i = 1; i < 10; i++) {
            if (sellDigital[i] == 0) {
                return i;
            }
        }
        return 100;//метод не отработал
    }

    // Метод установки программой хода в нужную ячейку цифрового массива (матрицы) sellDigital
    public static int[] pMatrix(int[] sellDigital, int more) {
        if (more == 100) return sellDigital;

        if (sellDigital[more] == 0) { // условие если ячейка пуста (=0) то в нее записываем 1
            sellDigital[more] = 5;
        }
        return sellDigital;
    }
}
