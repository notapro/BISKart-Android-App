package bis.kart.biskart;

public class CardData {

    Card []dataArray;

    CardData()
    {
        dataArray = new  Card[5];
        dataArray[0] = new Card("1234567891234567", 1, 2016, "555");
        dataArray[1] = new Card("2345678912345678", 1, 2016, "555");
        dataArray[2] = new Card("3456789123456789", 1, 2016, "555");
    }

    boolean paymentSuccessful(Card card)
    {
        boolean retVal = false;
        if(card.cardNum.equals(dataArray[0].cardNum))
        {
            if(card.month == dataArray[0].month)
            {
                if(card.year == dataArray[0].year)
                {
                    if(card.cvv.equals(dataArray[0].cvv))
                    {
                        retVal =true;
                    }

                }
            }
        }
        else if(card.cardNum.equals(dataArray[1].cardNum)) {
            if (card.month == dataArray[1].month) {
                if (card.year == dataArray[1].year) {
                    if(card.cvv.equals(dataArray[1].cvv))
                    {
                        retVal =true;
                    }

                }
            }
        }
        else if(card.cardNum.equals(dataArray[2].cardNum)) {
            if (card.month == dataArray[2].month) {
                if (card.year == dataArray[2].year) {
                    if(card.cvv.equals(dataArray[2].cvv))
                    {
                        retVal =true;
                    }

                }
            }
        }
        return retVal;
    }

}