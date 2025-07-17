package com.shiguretokita.reducing_fraction;

import java.util.Random;

public class JudgingRF {

    //メンバ変数
    private final Random rand;
    private int numerator,denominator; 


    //コンストラクタ
    public JudgingRF(int numerator,int denominator){
        this.rand=new Random();
        this.numerator=rand.nextInt(numerator)+1; //1から最大分子までの乱数生成
        this.denominator=rand.nextInt(denominator)+1; //1から最大分母までの乱数生成

    }


    //分子ゲッタ
    public int getNumerator(){
        return this.numerator;
    }


    //分母ゲッタ
    public int getDenominator(){
        return this.denominator;
    }


    //最大公約数を求めるメソッド
    private int gcd(int x,int y){
        int tmp;

        while((tmp=x%y)!=0){
            x=y;
            y=tmp;
        }

        return y;
    }


    //約分できるかの判定
    public boolean judge(){

        if(gcd(getNumerator(),getDenominator())==1) return false;

        else return true;
    }
    
}
