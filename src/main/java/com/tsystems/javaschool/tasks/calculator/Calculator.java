package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;

public class Calculator {

    public String evaluate(String data) {


        // Create parser object
        Parser calculator = new Parser();
        String expression =  data;

        // Read each string from input file
        // Send it to parser

        String result = null;
        while( true ){
            if( expression == null ){
                break;
            }
                result = null;
            try{
                result = calculator.calculate( calculator.negativeValueFix( expression ) );
                System.out.println( expression + " = " + result );
            }catch( Exception exception ){
                System.out.println( expression + " = " + result );
                System.out.println( exception );
            }

            return result;
        }
        return null;
    }

    public class Parser {
        // This function accept expression string and calculate result throw calling other methods
        public String calculate( String expression ) throws Exception{
            expression = openBrackets( expression );
            expression = calcMultDivSequences( expression );
            String result = calcSequence( expression );
            //operations so we can just calc it as sequence of operation with equal priority
            return result;
        }

        // this function should be called only once as very first method
        public String negativeValueFix( String expression ){
            expression.replace( '~', '!' );
            int index = 0;
            char currentChar;
            char prevChar = '(';
            while( index < expression.length() ){
                currentChar = expression.charAt( index );
                if( currentChar == '-' && prevChar != '(' ){
                    expression = expression.substring( 0, index ) +
                            "~" +
                            expression.substring( index + 1 );
                }else if( currentChar != ' ' ){
                    prevChar = currentChar;
                }
                index++;
            }
            return expression;
        }

        private String openBrackets( String expression )throws Exception{
            int index = expression.length() - 1;
            int lastRightBracketIndex = 0;
            int openedBracketsAmnt = 0;
            char currentChar;
            while( -1 < index  ){
                currentChar = expression.charAt(index);
                if( currentChar == ')'  ){
                    if( ++openedBracketsAmnt == 1 ){
                        lastRightBracketIndex = index;
                    }
                }else if( currentChar == '('  ){
                    if( --openedBracketsAmnt == 0 ){
                        expression = expression.substring( 0 , index ) +
                                calculate( expression.substring( index + 1, lastRightBracketIndex ) ) +
                                expression.substring( lastRightBracketIndex + 1 );
                    }
                }
                index--;
            }
            return expression;
        }


        public String calcMultDivSequences( String expression ) {
            int index = expression.length() - 1;
            int lastPlusOrMinusIndex = expression.length();
            char currentChar;
            while( -1 < index  ){
                currentChar = expression.charAt(index);
                if( currentChar == '+' || currentChar == '~'  ){
                    expression = expression.substring( 0 , index + 1 ) +
                            calcSequence( expression.substring( index + 1, lastPlusOrMinusIndex ) ) +
                            expression.substring( lastPlusOrMinusIndex );
                    lastPlusOrMinusIndex = index;
                }
                index--;
            }
            return expression;
        }

        // Important !1!  input string  should contain operations with equal priority
        private String calcSequence( String expression ){
            int index = 0;
            double result = 1.0;
            char prevSign = '*';
            int prevSignIndex = -1;
            char currentChar;
            while( true ){
                currentChar = expression.charAt( index );
                if( currentChar == '+' || currentChar == '~' || currentChar == '*' || currentChar == '/' ){
                    result = calc( result,
                            Double.parseDouble(expression.substring( prevSignIndex + 1, index )),
                            prevSign
                    );
                    prevSign = currentChar;
                    prevSignIndex = index;
                }else if( index == expression.length() - 1 ){
                    result = calc( result,
                            Double.parseDouble(expression.substring( prevSignIndex + 1, index + 1 )),
                            prevSign
                    );
                    break;
                }
                index++;
            }
            if(result == Double.POSITIVE_INFINITY) {
                return null;
            } else {
                DecimalFormat decimalFormat = new DecimalFormat("#.####");
                String secondResult = decimalFormat.format(result);
                System.out.println((secondResult));  // 7

                return secondResult.replaceAll(",", ".");
            }

        }

        // Just calc
        private double calc( double result,double number, char sign ){
            switch (sign){
                case '+':{
                    result += number;
                    break;
                }case '~':{
                    result -= number;
                    break;
                }case '*':{
                    result *= number;
                    break;
                }case '/':{
                    result /= number;
                    break;
                }default :{
                    System.out.println( "Wrong Sign" );
                }
            }
            return result;
        }
    }
}