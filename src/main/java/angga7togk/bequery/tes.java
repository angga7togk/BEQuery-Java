package angga7togk.bequery;

public class tes {

    public static void main(String[] args) {
        try {
            BEQuery query = BEQuery.connect("127.0.0.1");
            System.out.println(query.getHostName());
        }catch (BEQueryException e){
            System.out.println(e.getMessage());
        }
    }
}
