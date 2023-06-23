public class Columnize {
    public static String columnize(String[] input, int numberOfColumns) {
        int rowsCount = (int) Math.ceil(((double) input.length) / numberOfColumns);
        String[][] newArr = new String[rowsCount][numberOfColumns];
        int[] maxLength = new int[numberOfColumns];
        StringBuilder result = new StringBuilder();
        for (int i = 0; i<rowsCount; i++) {
            for (int j = 0;j<numberOfColumns && numberOfColumns*i+j<input.length; j++) {
                newArr[i][j] = input[(numberOfColumns*i)+j];
                maxLength[j] = Math.max(maxLength[j], input[(numberOfColumns*i)+j].length());
            }
        }
        for (int i = 0; i<rowsCount; i++) {
            for (int j = 0; j < numberOfColumns && numberOfColumns*i+j<input.length; j++) {
                result.append( newArr[i][j]);
                result.append(" ".repeat(maxLength[j] - newArr[i][j].length()));
                if (j!=numberOfColumns-1 && numberOfColumns*i+j<input.length-1) result.append(" | ");
            }
            if (i!=rowsCount-1) result.append("\n");
        }
        return result.toString();
    }
}
