package com.shiguretokita.reducing_fraction;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.shigure.reducing_fraction.JudgingRF;


@Controller
public class ReducingFractionController {

    private int max_numerator,max_denominator; //設定される最大の分子と分数
    private long current_time; //カウントダウン用変数
    private JudgingRF JRF; //問題出題,正誤判定用インスタンス
    private boolean RFflag; //正誤判定用フラグ
    private double score; //最終的なスコア


    //最大の分母と最大の分子を入力するフェーズ
    @GetMapping("/")
    public String getIndex(){
        return "/input";
    }

    
    //htmlからの入力を受け取り,クラス変数を設定し,問題出題フェーズへ移行
    @PostMapping("/")
    public String inputFraction(@RequestParam("max_numerator")Integer max_numerator,@RequestParam("max_denominator")Integer max_denominator,@RequestParam("current_time")String current_time){

        this.max_numerator=max_numerator;
        this.max_denominator=max_denominator;
        this.current_time=Long.parseLong(current_time);
        this.score=0;

        return "redirect:/judge_1";
    }


    //乱数をもとに問題を出題するフェーズ
    @GetMapping("/judge_1")
    public String getJudge_1(Model model){

        this.JRF=new JudgingRF(max_numerator,max_denominator);
        model.addAttribute("JRF",JRF);
        model.addAttribute("current_time",current_time);

        return "/judge_1";
    }


    //問題の回答を送信後,正誤判定フェーズへ移行
    @PostMapping("/judge_1")
    public String judgingFraction(@RequestParam("ReducingFraction")String RF){

        //送信された回答が"約分できる"ならRFflagをtrueとする
        if(RF.equals("possible")) this.RFflag=true;
        else this.RFflag=false;

        return "redirect:/judge_2";
    }


    //回答の正誤を判断するフェーズ
    @GetMapping("/judge_2")
    public String getJudge_2(Model model){

        boolean errata;

        //問題が正解した場合,点数を加算する
        if(JRF.judge()==RFflag){
            errata=true;
            score+=1;
        }
        else errata=false;

        model.addAttribute("JRF",JRF);
        model.addAttribute("errata",errata);
        model.addAttribute("current_time",current_time);

        return "/judge_2";
    }


    //最初の問題出力から60秒経過後,結果を表示
    @GetMapping("/result")
    public String getResult(Model model){
        
        model.addAttribute("score",score);

        return "/result";
    }


    //将来的にスコアをデータベースに保管する機能を作りたい

    //結果画面終了後,入力画面に移行
    @PostMapping("result")
    public String gotoInput(){
        return "redirect:/";
    }

}