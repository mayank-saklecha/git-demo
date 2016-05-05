import java.util.Arrays;

import java.util.Comparator;

public class Sol {

public static void main(String[] args) {

Integer a[] = { 1, 2, 10, 3, 4 };

Arrays.sort(a, new Comparator<Integer>() {

public int compare(Integer o1, Integer o2) {

return (o2 + "" + o1).compareTo(o1 + "" + o2);

}

});

String result = "";

for (int i : a) {

result += i;

}

System.out.println(result);

}

}
