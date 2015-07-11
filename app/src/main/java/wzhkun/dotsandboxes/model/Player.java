package wzhkun.dotsandboxes.model;

public abstract class Player {
    protected String name;
    protected Game game;

    public Player(String name) {
        this.name = name;
    }

    public static int indexIn(Player player, Player... players) {
        for (int i = 0; i < players.length; i++) {
            if (player == players[i])
                return i;
        }
        return -1;
    }

    public abstract Line move();

    public Game getGame() {
        return game;
    }

    public void addToGame(Game game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return !(name != null ? !name.equals(player.name) : player.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
