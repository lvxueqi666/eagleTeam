package com.example.qiqi.xianwan.game.wuziqi.ai;

import com.example.qiqi.xianwan.game.wuziqi.Coordinate;

public class EasyAi {
    /* 15*15共有572种五子连珠的可能性，该数组i和j表示棋盘位置，k表示其中一种五连珠可能性的ID号（1-527）
       所以每个数组元素的i和j就记录了当前这种可能性所经过的位置*/
    private boolean[][][] ptable = new boolean[15][15][572];  //玩家的可能性
    private boolean[][][] ctable = new boolean[15][15][572];  //电脑的可能性
    private int[][] win = new int[2][572];  //记录2位玩家所有可能的连珠数，-1则为永远无法5连珠
    private int[][] cgrades = new int[15][15];  //记录每格的分值
    private int[][] pgrades = new int[15][15];
    private int[][] board = new int[15][15];  	//记录棋盘

    //临时变量
    private int cgrade,pgrade;
    private int icount,m,n;
    private int mat,nat,mde,nde;

    public EasyAi(){
        initGame();
    }
    private void initGame()
    {
        //初始化棋盘
        for(int i=0;i<15;i++)
            for(int j=0;j<15;j++)
            {
                this.pgrades[i][j] = 0;
                this.cgrades[i][j] = 0;
                this.board[i][j] = 0;
            }
        //遍历所有的五连子可能情况的权值
        //横
        for(int i=0;i<15;i++)
            for(int j=0;j<11;j++){
                for(int k=0;k<5;k++){
                    this.ptable[j+k][i][icount] = true;
                    this.ctable[j+k][i][icount] = true;
                }
                icount++;
            }
        //竖
        for(int i=0;i<15;i++)
            for(int j=0;j<11;j++){
                for(int k=0;k<5;k++){
                    this.ptable[i][j+k][icount] = true;
                    this.ctable[i][j+k][icount] = true;
                }
                icount++;
            }
        //右斜
        for(int i=0;i<11;i++)
            for(int j=0;j<11;j++){
                for(int k=0;k<5;k++){
                    this.ptable[j+k][i+k][icount] = true;
                    this.ctable[j+k][i+k][icount] = true;
                }
                icount++;
            }
        //左斜
        for(int i=0;i<11;i++)
            for(int j=14;j>=4;j--){
                for(int k=0;k<5;k++){
                    this.ptable[j-k][i+k][icount] = true;
                    this.ctable[j-k][i+k][icount] = true;
                }
                icount++;
            }
        for(int i=0;i<=1;i++)  //初始化黑子白子上的每个权值上的连子数
            for(int j=0;j<572;j++)
                this.win[i][j] = 0;
        this.icount = 0;
        this.m = 0;
        this.n = 0;
    }

    public Coordinate comTurn(Coordinate c){     //找出电脑（白子）最佳落子点
        if(c != null){
            setPlayersPiece(c);
        }
        for(int i=0;i<15;i++)     //遍历棋盘上的所有坐标
            for(int j=0;j<15;j++){
                this.pgrades[i][j]=0;  //该坐标的黑子奖励积分清零
                if(this.board[i][j] == 0)  //在还没下棋子的地方遍历
                    for(int k=0;k<572;k++)    //遍历该棋盘可落子点上的黑子所有权值的连子情况，并给该落子点加上相应奖励分
                        if(this.ptable[i][j][k]){
                            switch(this.win[1][k]){
                                case 1: //一连子
                                    this.pgrades[i][j]+=5;
                                    break;
                                case 2: //两连子
                                    this.pgrades[i][j]+=50;
                                    break;
                                case 3: //三连子
                                    this.pgrades[i][j]+=180;
                                    break;
                                case 4: //四连子
                                    this.pgrades[i][j]+=400;
                                    break;
                            }
                        }
                this.cgrades[i][j]=0;//该坐标的白子的奖励积分清零
                if(this.board[i][j] == 0)  //在还没下棋子的地方遍历
                    for(int k=0;k<572;k++)     //遍历该棋盘可落子点上的白子所有权值的连子情况，并给该落子点加上相应奖励分
                        if(this.ctable[i][j][k]){
                            switch(this.win[0][k]){
                                case 1:  //一连子
                                    this.cgrades[i][j]+=5;
                                    break;
                                case 2:  //两连子
                                    this.cgrades[i][j]+=52;
                                    break;
                                case 3: //三连子
                                    this.cgrades[i][j]+=130;
                                    break;
                                case 4:  //四连子
                                    this.cgrades[i][j]+=10000;
                                    break;
                            }
                        }
            }
        if(c == null){      //开始时白子落子坐标
            m = 7;
            n = 7;
        }else{
            for(int i=0;i<15;i++)
                for(int j=0;j<15;j++)
                    if(this.board[i][j] == 0){  //找出棋盘上可落子点的黑子白子的各自最大权值，找出各自的最佳落子点
                        if(this.cgrades[i][j]>=this.cgrade){
                            this.cgrade = this.cgrades[i][j];
                            this.mat = i;
                            this.nat = j;
                        }
                        if(this.pgrades[i][j]>=this.pgrade){
                            this.pgrade = this.pgrades[i][j];
                            this.mde = i;
                            this.nde = j;
                        }
                    }
            if(this.cgrade>=this.pgrade){   //如果白子的最佳落子点的权值比黑子的最佳落子点权值大，则电脑的最佳落子点为白子的最佳落子点，否则相反
                m = mat;
                n = nat;
            }else{
                m = mde;
                n = nde;
            }
        }
        this.cgrade = 0;
        this.pgrade = 0;
        this.board[m][n] = 1;  //电脑下子位置
        for(int i=0;i<572;i++){
            if(this.ctable[m][n][i] && this.win[0][i] != -1)
                this.win[0][i]++;     //给白子的所有五连子可能的加载当前连子数
            if(this.ptable[m][n][i]){
                this.ptable[m][n][i] = false;
                this.win[1][i]= -1;
            }
        }
        Coordinate co = new Coordinate(m,n);
        return co;
    }

    private void setPlayersPiece(Coordinate c) {
        int m = c.x;
        int n = c.y;
        if(this.board[m][n] == 0){
            this.board[m][n] = 2;
            for(int i=0;i<572;i++){
                if(this.ptable[m][n][i] && this.win[1][i] != -1)
                    this.win[1][i]++;     //给黑子的所有五连子可能的加载当前连子数
                if(this.ctable[m][n][i]){
                    this.ctable[m][n][i] = false;
                    this.win[0][i]=-1;
                }
            }
        }

    }
}

