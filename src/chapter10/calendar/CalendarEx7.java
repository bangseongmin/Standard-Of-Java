package chapter10.calendar;

import java.util.Calendar;

public class CalendarEx7 {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage : java CalendarEx7 2015 11");
            return;
        }

        int year = Integer.parseInt(args[0]);
        int month = Integer.parseInt(args[1]);


        Calendar sDay = Calendar.getInstance();    // 시작일
        Calendar eDay = Calendar.getInstance();    // 끝일

        // 월의 경우 0부터 11까지의 값을 가지므로 1을 빼줘야 한다.
        sDay.set(year, month - 1, 1);
        eDay.set(year, month - 1, sDay.getActualMaximum(Calendar.DATE));

        sDay.add(Calendar.DATE, -sDay.get(Calendar.DAY_OF_WEEK) + 1);
        eDay.add(Calendar.DATE, 7 - eDay.get(Calendar.DAY_OF_WEEK));

        System.out.println("     " + year + "년 " + month + "월");
        System.out.println(" SU MO TU WE TH FR SA");

        // 시작일부터 마지막일까지 1일씩 증가시키면서 일을 출력
        for (int n = 1; sDay.before(eDay) || sDay.equals(eDay); sDay.add(Calendar.DATE, 1)) {
            int day = sDay.get(Calendar.DATE);
            System.out.print((day < 10) ? "  " + day : " " + day);
            if (n++ % 7 == 0) System.out.println();    // 7일치를 찍고 나서 줄을 바꾼다.
        }
    }
}
