package wzhkun.dotsandboxes.model;

/**
 * Created by wangzehao on 2015/7/6.
 */
public abstract class Player {
    public abstract Line move();
    private String name;

    public Player(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public static int indexIn(Player player,Player... players){
        for(int i=0;i<players.length;i++){
            if (player==players[i])
                return i;
        }
        return -1;
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
