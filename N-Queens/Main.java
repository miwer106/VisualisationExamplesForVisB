import java.io.PrintWriter;

public class Main {
    private static int N = 120;
    public static void main(String[] args){
        printJSON();
        printSVG();
    }

    public static void printJSON(){
        try {
            PrintWriter writer = new PrintWriter("queens_"+N+".json", "UTF-8");
            writer.println("{\n" +
                    "  \"svg\":\"queens_"+N+".svg\",\n" +
                    "  \"items\": [");
            for(int i = 1; i <= N; i++) {
                StringBuilder value = new StringBuilder();
                int j;
                for(j = 2; j <= N; j++){
                    if(j == 2){
                        value.append("IF ");
                    } else{
                        value.append(" ELSIF");
                    }
                    value.append(" "+i+"|->"+j+":queens THEN \\\""+((j-1)*45)+"\\\"");
                }
                value.append(" ELSE \\\"0\\\" END");
                writer.println("\t{\n" +
                        "      \"id\": \"svgQueen"+i+"\",\n" +
                        "      \"attr\": \"visibility\",\n" +
                        "      \"value\" : \"IF "+i+":dom(queens) THEN \\\"visible\\\" ELSE \\\"hidden\\\" END\"\n" +
                        "    },\n");
                writer.println("\t{\n" +
                        "      \"id\": \"svgQueen"+i+"\",\n" +
                        "      \"attr\": \"y\",\n" +
                        "      \"value\" :\""+ value.toString() +"\"\n"+
                        "    },\n");
                writer.println("\t{\n" +
                        "      \"id\": \"svgQueen"+i+"\",\n" +
                        "      \"attr\": \"fill\",\n" +
                        "      \"value\" : \"IF is_attacked("+i+") & "+i+":dom(queens) THEN \\\"red\\\" ELSE \\\"black\\\" END\"\n" +
                        "    },\n");
                if(i == N){
                    writer.println("\t{\n" +
                            "      \"id\": \"gTiles"+i+"\",\n" +
                            "      \"attr\": \"visibility\",\n" +
                            "      \"value\" : \"IF "+i+"<=n  THEN \\\"visible\\\" ELSE \\\"hidden\\\" END\"\n" +
                            "    }\n");
                } else{
                    writer.println("\t{\n" +
                            "      \"id\": \"gTiles"+i+"\",\n" +
                            "      \"attr\": \"visibility\",\n" +
                            "      \"value\" : \"IF "+i+"<=n  THEN \\\"visible\\\" ELSE \\\"hidden\\\" END\"\n" +
                            "    },\n");
                }
            }
            writer.println("], \n" +
                    "\"events\": [");
            for(int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if((i == N && j == N)){
                        writer.println("\t{\n" +
                                "      \"id\": \"tile"+j+"x"+i+"\",\n" +
                                "      \"event\": \"TryQueen\",\n" +
                                "      \"predicates\" : [\"i="+j+"\",\"j="+i+"\"] \n" +
                                "    }\n");
                    } else {
                        writer.println("\t{\n" +
                                "      \"id\": \"tile"+j+"x"+i+"\",\n" +
                                "      \"event\": \"TryQueen\",\n" +
                                "      \"predicates\" : [\"i="+j+"\",\"j="+i+"\"] \n" +
                                "    },\n");
                    }
                }
            }
            writer.println("  ]\n" +
                    "}");
            writer.println();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void printSVG(){
        try {
            PrintWriter writer = new PrintWriter("queens_"+N+".svg", "UTF-8");
            writer.println("<svg width=\""+45*N+"\" height=\""+45*N+"\"\n" +
                    "  xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
            for(int k = 1; k <= N; k++) {
                writer.println("<g id=\"gTiles"+k+"\">");
                for (int i = 1; i <= k; i++) {
                    for (int j = 1; j <= k; j++) {
                        int x = (i-1)*45;
                        int y = (j-1)*45;
                        if(!(i < k && j < k)){
                            if ((i + j) % 2 != 0) {
                                writer.println("<svg width=\"45\" height=\"45\" x=\"" + x + "\" y=\"" + y + "\">\n" +
                                        "    <g id=\"tile" + i + "x" + j + "\" style=\"opacity:1; fill:000000; fill-opacity:1; fill-rule:evenodd; stroke:#000000; stroke-width:1.5; stroke-linecap:round;stroke-linejoin:round;stroke-miterlimit:4; stroke-dasharray:none; stroke-opacity:1;\">\n" +
                                        "      <rect x=\"0\" y=\"0\" width=\"45\" height=\"45\" style=\"fill:#d18b47; stroke:none;\"  />\n" +
                                        "    </g>\n" +
                                        "  </svg>");
                            } else {
                                writer.println("  <svg width=\"45\" height=\"45\" x=\"" + x + "\" y=\"" + y + "\">\n" +
                                        "  <g id=\"tile" + i + "x" + j + "\" style=\"opacity:1; fill:000000; fill-opacity:1; fill-rule:evenodd; stroke:#000000; stroke-width:1.5; stroke-linecap:round;stroke-linejoin:round;stroke-miterlimit:4; stroke-dasharray:none; stroke-opacity:1;\">\n" +
                                        "    <rect x=\"0\" y=\"0\" width=\"45\" height=\"45\" style=\"fill:#ffce9e; stroke:none;\"  />\n" +
                                        "  </g>\n" +
                                        "</svg>");
                            }
                        }
                    }
                }
                writer.println("</g>");
            }
            for(int i = 1; i <= N; i++){
                writer.println("<svg id=\"svgQueen"+i+"\" width=\"45\" height=\"45\" x=\""+45*(i-1)+"\" y=\"0\">\n" +
                        "<g id=\"queen"+i+"\">\n" +
                        "      <g style=\"fill:#000000; stroke:none;\">\n" +
                        "        <circle cx=\"6\"    cy=\"12\" r=\"2.75\" />\n" +
                        "        <circle cx=\"14\"   cy=\"9\"  r=\"2.75\" />\n" +
                        "        <circle cx=\"22.5\" cy=\"8\"  r=\"2.75\" />\n" +
                        "        <circle cx=\"31\"   cy=\"9\"  r=\"2.75\" />\n" +
                        "        <circle cx=\"39\"   cy=\"12\" r=\"2.75\" />\n" +
                        "      </g>\n" +
                        "      <path\n" +
                        "         d=\"M 9,26 C 17.5,24.5 30,24.5 36,26 L 38.5,13.5 L 31,25 L 30.7,10.9 L 25.5,24.5 L 22.5,10 L 19.5,24.5 L 14.3,10.9 L 14,25 L 6.5,13.5 L 9,26 z\"\n" +
                        "         style=\"stroke-linecap:butt; stroke:#000000;\" />\n" +
                        "      <path\n" +
                        "         d=\"M 9,26 C 9,28 10.5,28 11.5,30 C 12.5,31.5 12.5,31 12,33.5 C 10.5,34.5 10.5,36 10.5,36 C 9,37.5 11,38.5 11,38.5 C 17.5,39.5 27.5,39.5 34,38.5 C 34,38.5 35.5,37.5 34,36 C 34,36 34.5,34.5 33,33.5 C 32.5,31 32.5,31.5 33.5,30 C 34.5,28 36,28 36,26 C 27.5,24.5 17.5,24.5 9,26 z\"\n" +
                        "         style=\"stroke-linecap:butt;\" />\n" +
                        "      <path\n" +
                        "         d=\"M 11,38.5 A 35,35 1 0 0 34,38.5\"\n" +
                        "         style=\"fill:none; stroke:#000000; stroke-linecap:butt;\" />\n" +
                        "      <path\n" +
                        "         d=\"M 11,29 A 35,35 1 0 1 34,29\"\n" +
                        "         style=\"fill:none; stroke:#ffffff;\" />\n" +
                        "      <path\n" +
                        "         d=\"M 12.5,31.5 L 32.5,31.5\"\n" +
                        "         style=\"fill:none; stroke:#ffffff;\" />\n" +
                        "      <path\n" +
                        "         d=\"M 11.5,34.5 A 35,35 1 0 0 33.5,34.5\"\n" +
                        "         style=\"fill:none; stroke:#ffffff;\" />\n" +
                        "      <path\n" +
                        "         d=\"M 10.5,37.5 A 35,35 1 0 0 34.5,37.5\"\n" +
                        "         style=\"fill:none; stroke:#ffffff;\" />\n" +
                        "       </g>\n" +
                        "</svg>");
            }
            writer.println("</svg>");
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}