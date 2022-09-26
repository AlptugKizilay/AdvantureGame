import java.util.concurrent.ThreadLocalRandom;

public abstract class BattleLoc extends Location {
    protected Obstacle obstacle;
    protected String award;

    BattleLoc(Player player, String name, Obstacle obstacle, String award) {
        super(player);
        this.obstacle = obstacle;
        this.name = name;
        this.award = award;
    }

    public boolean getLocation() {
        int obsCount = obstacle.count();
        System.out.println("�uan buradas�n�z : " + this.getName());
        System.out.println("Dikkatli ol! Burada " + obsCount + " tane " + obstacle.getName() + " ya��yor !");
        System.out.print("<S>ava� veya <K>a� :");
        String selCase = scan.nextLine();
        selCase = selCase.toUpperCase();
        if (selCase.equals("S")) {
            if (combat(obsCount)) {
                System.out.println(this.getName() + " b�lgesindeki t�m d��manlar� temizlediniz !");
                if (this.award.equals("Food") && player.getInv().isFood() == false) {
                    System.out.println(this.award + " Kazand�n�z! ");
                    player.getInv().setFood(true);
                } else if (this.award.equals("Water") && player.getInv().isWater() == false) {
                    System.out.println(this.award + " Kazand�n�z! ");
                    player.getInv().setWater(true);
                } else if (this.award.equals("Firewood") && player.getInv().isFirewood() == false) {
                    System.out.println(this.award + " Kazand�n�z! ");
                    player.getInv().setFirewood(true);
                }
                return true;
            }

            if (player.getHealthy() <= 0) {
                System.out.println("�ld�n�z !");
                return false;
            }

        }
        return true;
    }

    public boolean combat(int obsCount) {
        for (int i = 0; i < obsCount; i++) {
            int defObsHealth = obstacle.getHealth();
            playerStats();
            enemyStats();
            while (player.getHealthy() > 0 && obstacle.getHealth() > 0) {
                System.out.print("<V>ur veya <K>a� :");
                String selCase = scan.nextLine();
                selCase = selCase.toUpperCase();
                if (selCase.equals("V")) {
                    int randomNum = ThreadLocalRandom.current().nextInt(1, 2 + 1);
                    if (randomNum == 1) {
                        System.out.println("Siz vurdunuz !");
                        obstacle.setHealth(obstacle.getHealth() - player.getTotalDamage());
                        afterHit();
                        if (obstacle.getHealth() > 0) {
                            System.out.println();
                            System.out.println("Canavar size vurdu !");
                            player.setHealthy(player.getHealthy() - (obstacle.getDamage() - player.getInv().getArmor()));
                            afterHit();

                        }

                    } else {
                        System.out.println("Canavar size vurdu !");
                        player.setHealthy(player.getHealthy() - (obstacle.getDamage() - player.getInv().getArmor()));
                        afterHit();
                        if (obstacle.getHealth() > 0) {
                            System.out.println();
                            System.out.println("Siz vurdunuz !");
                            obstacle.setHealth(obstacle.getHealth() - player.getTotalDamage());
                            afterHit();

                        }
                    }

                } else {
                    return false;
                }
            }

            if (obstacle.getHealth() < player.getHealthy()) {
                System.out.println("D��man� yendiniz !");
                System.out.println("********************");
                chanceAward();
                System.out.println("**************");
                player.setMoney(player.getMoney() + obstacle.getAward());
                System.out.println("G�ncel Paran�z : " + player.getMoney());
                obstacle.setHealth(defObsHealth);
            } else {
                return false;
            }
            System.out.println("-------------------");
        }
        return true;
    }

    public void playerStats() {
        System.out.println("Oyuncu De�erleri\n--------------");
        System.out.println("Can:" + player.getHealthy());
        System.out.println("Hasar:" + player.getTotalDamage());
        System.out.println("Para:" + player.getMoney());
        if (player.getInv().getDamage() > 0) {
            System.out.println("Silah:" + player.getInv().getwName());
        }
        if (player.getInv().getArmor() > 0) {
            System.out.println("Z�rh:" + player.getInv().getaName());
        }
    }

    public void enemyStats() {
        System.out.println("\n" + obstacle.getName() + " De�erleri\n--------------");
        System.out.println("Can:" + obstacle.getHealth());
        System.out.println("Hasar:" + obstacle.getDamage());
        System.out.println("�d�l:" + obstacle.getAward());
    }

    public void afterHit() {
        System.out.println("Oyuncu Can�:" + player.getHealthy());
        System.out.println(obstacle.getName() + " Can�:" + obstacle.getHealth());
        System.out.println();
    }

    public void chanceAward() {
        int percent = ThreadLocalRandom.current().nextInt(1, 100 + 1);

        if (percent > 0 && percent <= 15) {
            System.out.println("Silah kazand�n�z!");
            int perGun = ThreadLocalRandom.current().nextInt(1, 10 + 1);
            if (perGun > 0 && perGun <= 2) {
                System.out.println("T�fek Kazand�n�z!");
                player.getInv().setwName("T�fek");
                player.getInv().setDamage(7);
                System.out.println("Yeni Hasar :" + player.getTotalDamage());
            }
            else if (perGun > 2 && perGun <= 5) {
                System.out.println("K�l�� Kazand�n�z!");
                player.getInv().setwName("K�l��");
                player.getInv().setDamage(3);
                System.out.println("Yeni Hasar :" + player.getTotalDamage());
            } else {
                System.out.println("Tabanca Kazand�n�z!");
                player.getInv().setwName("Tabanca");
                player.getInv().setDamage(2);
                System.out.println("Yeni Hasar :" + player.getTotalDamage());
            }
        } else if (percent > 15 && percent <= 30) {
            System.out.println("Z�rh kazand�n�z!");
            int perArmour = ThreadLocalRandom.current().nextInt(1,10+1);
            if (perArmour > 0 && perArmour <=2){
                System.out.println("Ag�r Armor Kazand�n�z!");
                player.getInv().setaName("A��r Armor");
                player.getInv().setArmor(5);
                System.out.println("Engellenen Hasar : " + player.getInv().getArmor());

            } else if (perArmour>2 && perArmour<=5) {
                System.out.println("Orta Armor Kazand�n�z!");
                player.getInv().setaName("Orta Armor");
                player.getInv().setArmor(3);
                System.out.println("Engellenen Hasar : " + player.getInv().getArmor());
            }else{
                System.out.println("Hafif Armor Kazand�n�z!");
                player.getInv().setaName("Orta Armor");
                player.getInv().setArmor(1);
                System.out.println("Engellenen Hasar : " + player.getInv().getArmor());
            }

        } else if (percent > 30 && percent <= 55) {
            int perMoney = ThreadLocalRandom.current().nextInt(1,10+1);
            if (perMoney > 0 && perMoney <=2){
                System.out.println("10 para kazand�n�z!");
                player.setMoney(player.getMoney() + 10);

            } else if (perMoney>2 && perMoney<=5) {
                System.out.println("5 para kazand�n�z!");
                player.setMoney(player.getMoney() + 5);

            }else{
                System.out.println("1 para kazand�n�z!");
                player.setMoney(player.getMoney() + 1);

            }

        } else {
            System.out.println("�d�l Kazanamad�n�z");

        }
    }

}
