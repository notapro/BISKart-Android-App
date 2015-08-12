package bis.kart.biskart;

public class Card {

    String cardNum;
    int month;
    int year;
    String cvv;

    Card(String cardNum, int month, int year, String cvv)
    {
        this.cardNum = cardNum;
        this.month = month;
        this.year = year;
        this.cvv = cvv;
    }

    boolean isValidFormat(String cardNum, int month, int year, String cvv)
    {
        if(cardNum.length()<16 || cvv.length() < 3)
        {
            return false;
        }
        return true;
    }
}