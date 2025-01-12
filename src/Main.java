import java.util.Scanner;

class PlayerStatus {
    // TODO

    private String nickname;
    private int score;
    private int lives;
    private int health;
    private String weaponInHand;
    private double positionX;
    private double positionY;

    private static String gameName;

    // The method that initialize only the player name
    public void initPlayer(String nickname) {
        this.nickname = nickname;
        this.health = 100;
    }

    // The second method that initialize the player name and lives
    public void initPlayer(String nickname, int lives) {
        this.nickname = nickname;
        this.lives = lives;
        this.health = 100;
    }

    // The third method that initialize the player name, lives and also score
    public void initPlayer(String nickname, int lives, int score) {
        this.nickname = nickname;
        this.lives = lives;
        this.score = score;
        this.health = 100;
    }

    public String getNickname() {
        return this.nickname;
    }

    public int getScore() {
        return this.score;
    }

    public int getLives() {
        return this.lives;
    }

    public int getHealth() {
        return this.health;
    }

    public static String getGameName() {
        return gameName;
    }

    public static void setGameName(String gameName) {
        PlayerStatus.gameName = gameName;
    }

    public String getWeaponInHand() {
        return this.weaponInHand;
    }

    public boolean setWeaponInHand(String weaponInHand) {
        int cost = 0;

        // Check if the weapon is valid, and set it's cost
        switch (weaponInHand) {
            case "knife":
                cost = 1000;
                break;
            case "sniper":
                cost = 10000;
                break;
            case "kalashnikov":
                cost = 20000;
                break;
            default:
                return false;
        }

        // Check if the player has the score big enough to get the weapon
        if (this.score >= cost) {
            this.score -= cost;
            this.weaponInHand = weaponInHand;
            return true;
        } else {
            return false;
        }
    }

    public void movePlayerTo(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public double distantaOponent(PlayerStatus adversar) {
        return Math.sqrt(Math.pow(this.positionX - adversar.positionX, 2) + Math.pow(this.positionY - adversar.positionY, 2));
    }

    // Checking if a number is perfect. We will use this method inside findArtifact method to update the player status.
    private boolean numarPerfect(int numar) {
        int suma = 1;
        for (int i = 2; i <= numar / 2; i++) {
            if (numar % i == 0) {
                suma += i;
            }
        }
        return suma == numar;
    }

    // Checking if a number is prime. We will use this method inside findArtifact method to update the player status.
    private boolean numarPrim(int numar) {
        if (numar <= 1) {
            return false;
        }
        for (int i = 2; i <= numar / 2; i++) {
            if (numar % i == 0) {
                return false;
            }
        }
        return true;
    }

    // Calculating the sum of the digits of a number.
    // We will use this method inside findArtifact method to update the player status.
    private int sumaCifrelor(int numar) {
        int suma = 0;
        while (numar != 0) {
            suma += numar % 10;
            numar /= 10;
        }
        return suma;
    }

    // Updating the life of the player and taking care of the life decrement and game over.
    private void updateViata(int viata) {
        this.health -= viata;
        if (this.health == 0) {
            this.lives -= 1;
            if (this.lives > 0) {
                this.health = 100;
            } else {
                System.out.println("Game Over");
                this.health = 0;
            }
        }
    }

    // Updating the player status based on the artifact found.
    public void findArtifact(int artifactCode) {
        if (numarPerfect(artifactCode)) {
            this.score += 5000;
            this.lives += 1;
            this.health = 100;
        } else if (numarPrim(artifactCode)) {
            this.score += 1000;
            this.lives += 2;
            this.health += 25;
            if (this.health > 100) {
                this.health = 100;
            }
        } else if (artifactCode % 2 == 0 && sumaCifrelor(artifactCode) % 3 == 0) {
            this.score -= 3000;
            updateViata(25);
        } else {
            this.score += artifactCode;
        }
    }

    // Checking if the player should attack the opponent based on the weapon and the probability to win
    public boolean shouldAttackOpponent(PlayerStatus opponent) {
        double distanta = distantaOponent(opponent);
        if (this.weaponInHand.equals(opponent.weaponInHand)) {
            double probabilitatePlayer = (3 * this.health + this.score / 1000.0) / 4;
            double probabilitateOponent = (3 * opponent.health + opponent.score / 1000.0) / 4;
            return probabilitatePlayer > probabilitateOponent;
        } else {
            if (distanta > 1000) {
                return armaMaiBuna(opponent, "sniper", "kalashnikov", "knife");
            } else {
                return armaMaiBuna(opponent, "kalashnikov", "sniper", "knife");
            }
        }
    }

    // Checking who has the better weapon for a fight.
    private boolean armaMaiBuna(PlayerStatus opponent, String puternic, String mediu, String slab) {
        if (this.weaponInHand.equals(puternic)) {
            return true;
        } else if (this.weaponInHand.equals(mediu) && opponent.weaponInHand.equals(slab)) {
            return true;
        } else {
            return false;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCase = scanner.nextInt();
        PlayerStatus player1;
        PlayerStatus player2;
        PlayerStatus player3;

        switch (testCase) {
            case 0:
                // Sanity check
                System.out.println("Sanity check");
                break;
            case 1:
                // Check fields, initializers, getters, setters
                player1 = new PlayerStatus();
                player1.initPlayer("player1");
                player1.initPlayer("player1", 1, 999999);
                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1);
                player2.initPlayer("player2", 1, 9999999);
                player3 = new PlayerStatus();
                player3.initPlayer("player3", 2, 9999999);

                System.out.println(player1.getNickname());
                System.out.println(player2.getNickname());
                System.out.println(player3.getNickname());

                PlayerStatus.setGameName("World of DevMind");
                System.out.println(PlayerStatus.getGameName());
                PlayerStatus.setGameName("Call of DevMind");
                System.out.println(PlayerStatus.getGameName());

                player1.setWeaponInHand("knife");
                System.out.println(player1.getWeaponInHand());
                player1.setWeaponInHand("lightsaber");
                System.out.println(player1.getWeaponInHand());
                player2.setWeaponInHand("kalashnikov");
                System.out.println(player2.getWeaponInHand());
                player3.setWeaponInHand("sniper");
                System.out.println(player3.getWeaponInHand());
                break;
            case 2:
                // Test findArtifact for traps and player death
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999);

                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);
                break;
            case 3:
                // Test findArtifact for traps and gaining lives
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999);

                player1.findArtifact(28);
                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);

                System.out.println("empty");
                break;
            case 4:
                // Test findArtifact for gaining score and spending it on weapons
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 0);

                player1.findArtifact(5000);
                System.out.println(player1.setWeaponInHand("sniper"));
                player1.findArtifact(5000);
                System.out.println(player1.setWeaponInHand("sniper"));
                System.out.println(player1.setWeaponInHand("sniper"));
                player1.findArtifact(19000);
                System.out.println(player1.setWeaponInHand("kalashnikov"));
                player1.findArtifact(1000);
                System.out.println(player1.setWeaponInHand("kalashnikov"));
                System.out.println(player1.getWeaponInHand());
                break;
            case 5:
                // Test shouldAttackOpponent for close distance different weapon duel
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999999);

                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 999999999);

                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(100, 100);

                player1.setWeaponInHand("knife");
                player2.setWeaponInHand("sniper");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("kalashnikov");
                System.out.println(player1.shouldAttackOpponent(player2));

                player1.setWeaponInHand("sniper");
                player2.setWeaponInHand("knife");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("kalashnikov");
                System.out.println(player1.shouldAttackOpponent(player2));

                player1.setWeaponInHand("kalashnikov");
                player2.setWeaponInHand("knife");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("sniper");
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
            case 6:
                // Test shouldAttackOpponent for close distance different weapon duel
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999999);

                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 999999999);

                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(800, 800);

                player1.setWeaponInHand("knife");
                player2.setWeaponInHand("sniper");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("kalashnikov");
                System.out.println(player1.shouldAttackOpponent(player2));

                player1.setWeaponInHand("sniper");
                player2.setWeaponInHand("knife");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("kalashnikov");
                System.out.println(player1.shouldAttackOpponent(player2));

                player1.setWeaponInHand("kalashnikov");
                player2.setWeaponInHand("knife");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("sniper");
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
            case 7:
                // Test shouldAttackOpponent for same weapon duel
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 9999);

                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 999999999);

                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(10, 10);

                player1.setWeaponInHand("knife");
                player2.setWeaponInHand("knife");

                System.out.println(player1.shouldAttackOpponent(player2));
                player2.movePlayerTo(1000, 1000);
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
            case 8:
                // Test shouldAttackOpponent for same weapon duel
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999999);

                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 99999);

                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(10, 10);

                player1.setWeaponInHand("sniper");
                player2.setWeaponInHand("sniper");

                System.out.println(player1.shouldAttackOpponent(player2));
                player2.movePlayerTo(1000, 1000);
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
            case 9:
                // Test shouldAttackOpponent for same weapon duel with different health
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999);

                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 999900);

                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(10, 10);

                player1.setWeaponInHand("kalashnikov");
                player2.setWeaponInHand("kalashnikov");

                System.out.println(player1.shouldAttackOpponent(player2));
                player1.findArtifact(300);
                player2.movePlayerTo(1000, 1000);
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
            case 10:
                // Test shouldAttackOpponent for same weapon duel with different health
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999);

                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 999900);

                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(10, 10);

                player1.setWeaponInHand("sniper");
                player2.setWeaponInHand("sniper");

                System.out.println(player1.shouldAttackOpponent(player2));

                player1.findArtifact(300);
                System.out.println(player1.shouldAttackOpponent(player2));

                player1.findArtifact(7);
                System.out.println(player1.shouldAttackOpponent(player2));

                player1.findArtifact(2000);
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
        }
    }
}