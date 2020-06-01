package com.example.qiqi.xianwan.game.wuziqi.ai;

public class ChessModel {
    //棋盘的宽度、高度、棋盘的模式（如20×15）
    private int width,height,modeChess;
    //棋盘方格的横向、纵向坐标
    private int x=0,y=0;
    //棋盘方格的横向、纵向坐标所对应的棋子颜色，
    //数组arrMapShow只有3个值：1，2，3，-5，
    //其中1代表该棋盘方格上下的棋子为黑子，
    //2代表该棋盘方格上下的棋子为白子，
    //3代表为该棋盘方格上没有棋子，
    //-5代表该棋盘方格不能够下棋子
    private int[][] arrMapShow;
    //交换棋手的标识，棋盘方格上是否有棋子的标识符
    private boolean isOdd,isExist;

    public ChessModel() {}

    //该构造方法根据不同的棋盘模式（modeChess）来构建对应大小的棋盘
    public ChessModel(int modeChess){
        this.isOdd=true;
        if(modeChess == 1){
            PanelInit(20, 15, modeChess);
        }
        if(modeChess == 2){
            PanelInit(30, 20, modeChess);
        }
        if(modeChess == 3){
            PanelInit(40, 30, modeChess);
        }
    }

    //按照棋盘模式构建棋盘大小
    private void PanelInit(int width, int height, int modeChess){
        this.width = width;
        this.height = height;
        this.modeChess = modeChess;
        arrMapShow = new int[width+1][height+1];
        for(int i = 0; i <= width; i++){
            for(int j = 0; j <= height; j++){
                arrMapShow[i][j] = -5;
            }
        }
    }

    //获取是否交换棋手的标识符
    public boolean getisOdd(){
        return this.isOdd;
    }

    //设置交换棋手的标识符
    public void setisOdd(boolean isodd){
        if(isodd)
            this.isOdd=true;
        else
            this.isOdd=false;
    }

    //获取某棋盘方格是否有棋子的标识值
    public boolean getisExist(){
        return this.isExist;
    }

    //获取棋盘宽度
    public int getWidth(){
        return this.width;
    }

    //获取棋盘高度
    public int getHeight(){
        return this.height;
    }

    //获取棋盘模式
    public int getModeChess(){
        return this.modeChess;
    }

    //获取棋盘方格上棋子的信息
    public int[][] getarrMapShow(){
        return arrMapShow;
    }

    //判断下子的横向、纵向坐标是否越界
    private boolean badxy(int x, int y){
        if(x >= width+20 || x < 0)
            return true;
        return y >= height+20 || y < 0;
    }

    //计算棋盘上某一方格上八个方向棋子的最大值，
    //这八个方向分别是：左、右、上、下、左上、左下、右上、右下
    public boolean chessExist(int i,int j){
        if(this.arrMapShow[i][j]==1 || this.arrMapShow[i][j]==2)
            return true;
        return false;
    }

    //判断该坐标位置是否可下棋子
    public void readyplay(int x,int y){
        if(badxy(x,y))
            return;
        if (chessExist(x,y))
            return;
        this.arrMapShow[x][y]=3;
    }

    //在该坐标位置下棋子
    public void play(int x,int y){
        if(badxy(x,y))
            return;
        if(chessExist(x,y)){
            this.isExist=true;
            return;
        }else
            this.isExist=false;
        if(getisOdd()){
            setisOdd(false);
            this.arrMapShow[x][y]=1;
        }else{
            setisOdd(true);
            this.arrMapShow[x][y]=2;
        }
    }

    //计算机走棋
    /*
     *说明：用穷举法判断每一个坐标点的四个方向的的最大棋子数，
     *最后得出棋子数最大值的坐标，下子
     **/
    public void computerDo(int width,int height){
        int max_black,max_white,max_temp,max=0;
        setisOdd(true);
        System.out.println("计算机走棋 ...");
        for(int i = 0; i <= width; i++){
            for(int j = 0; j <= height; j++){
                if(!chessExist(i,j)){//算法判断是否下子
                    max_white=checkMax(i,j,2);//判断白子的最大值
                    max_black=checkMax(i,j,1);//判断黑子的最大值
                    max_temp=Math.max(max_white,max_black);
                    if(max_temp>max){
                        max=max_temp;
                        this.x=i;
                        this.y=j;
                    }
                }
            }
        }
        setX(this.x);
        setY(this.y);
        this.arrMapShow[this.x][this.y]=2;
    }

    //记录电脑下子后的横向坐标
    public void setX(int x){
        this.x=x;
    }

    //记录电脑下子后的纵向坐标
    public void setY(int y){
        this.y=y;
    }

    //获取电脑下子的横向坐标
    public int getX(){
        return this.x;
    }

    //获取电脑下子的纵向坐标
    public int getY(){
        return this.y;
    }

    //计算棋盘上某一方格上八个方向棋子的最大值，
    //这八个方向分别是：左、右、上、下、左上、左下、右上、右下
    public int checkMax(int x, int y,int black_or_white){
        int num=0,max_num,max_temp=0;
        int x_temp=x,y_temp=y;
        int x_temp1=x_temp,y_temp1=y_temp;
        //judge right
        for(int i=1;i<5;i++){
            x_temp1+=1;
            if(x_temp1>this.width)
                break;
            if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
                num++;
            else
                break;
        }
        //judge left
        x_temp1=x_temp;
        for(int i=1;i<5;i++){
            x_temp1-=1;
            if(x_temp1<0)
                break;
            if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
                num++;
            else
                break;
        }
        if(num<5)
            max_temp=num;

        //judge up
        x_temp1=x_temp;
        y_temp1=y_temp;
        num=0;
        for(int i=1;i<5;i++){
            y_temp1-=1;
            if(y_temp1<0)
                break;
            if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
                num++;
            else
                break;
        }
        //judge down
        y_temp1=y_temp;
        for(int i=1;i<5;i++){
            y_temp1+=1;
            if(y_temp1>this.height)
                break;
            if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
                num++;
            else
                break;
        }
        if(num>max_temp&&num<5)
            max_temp=num;

        //judge left_up
        x_temp1=x_temp;
        y_temp1=y_temp;
        num=0;
        for(int i=1;i<5;i++){
            x_temp1-=1;
            y_temp1-=1;
            if(y_temp1<0 || x_temp1<0)
                break;
            if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
                num++;
            else
                break;
        }
        //judge right_down
        x_temp1=x_temp;
        y_temp1=y_temp;
        for(int i=1;i<5;i++){
            x_temp1+=1;
            y_temp1+=1;
            if(y_temp1>this.height || x_temp1>this.width)
                break;
            if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
                num++;
            else
                break;
        }
        if(num>max_temp&&num<5)
            max_temp=num;

        //judge right_up
        x_temp1=x_temp;
        y_temp1=y_temp;
        num=0;
        for(int i=1;i<5;i++){
            x_temp1+=1;
            y_temp1-=1;
            if(y_temp1<0 || x_temp1>this.width)
                break;
            if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
                num++;
            else
                break;
        }
        //judge left_down
        x_temp1=x_temp;
        y_temp1=y_temp;
        for(int i=1;i<5;i++){
            x_temp1-=1;
            y_temp1+=1;
            if(y_temp1>this.height || x_temp1<0)
                break;
            if(this.arrMapShow[x_temp1][y_temp1]==black_or_white)
                num++;
            else
                break;
        }
        if(num>max_temp&&num<5)
            max_temp=num;
        max_num=max_temp;
        return max_num;
    }

    //判断胜负
    public boolean judgeSuccess(int x,int y,boolean isodd){
        int num=1;
        int arrvalue;
        int x_temp=x,y_temp=y;
        if(isodd)
            arrvalue=2;
        else
            arrvalue=1;
        int x_temp1=x_temp,y_temp1=y_temp;
        //判断右边
        for(int i=1;i<6;i++){
            x_temp1+=1;
            if(x_temp1>this.width)
                break;
            if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
                num++;
            else
                break;
        }
        //判断左边
        x_temp1=x_temp;
        for(int i=1;i<6;i++){
            x_temp1-=1;
            if(x_temp1<0)
                break;
            if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
                num++;
            else
                break;
        }
        if(num==5)
            return true;

        //判断上方
        x_temp1=x_temp;
        y_temp1=y_temp;
        num=1;
        for(int i=1;i<6;i++){
            y_temp1-=1;
            if(y_temp1<0)
                break;
            if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
                num++;
            else
                break;
        }
        //判断下方
        y_temp1=y_temp;
        for(int i=1;i<6;i++){
            y_temp1+=1;
            if(y_temp1>this.height)
                break;
            if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
                num++;
            else
                break;
        }
        if(num==5)
            return true;

        //判断左上
        x_temp1=x_temp;
        y_temp1=y_temp;
        num=1;
        for(int i=1;i<6;i++){
            x_temp1-=1;
            y_temp1-=1;
            if(y_temp1<0 || x_temp1<0)
                break;
            if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
                num++;
            else
                break;
        }
        //判断右下
        x_temp1=x_temp;
        y_temp1=y_temp;
        for(int i=1;i<6;i++){
            x_temp1+=1;
            y_temp1+=1;
            if(y_temp1>this.height || x_temp1>this.width)
                break;
            if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
                num++;
            else
                break;
        }
        if(num==5)
            return true;

        //判断右上
        x_temp1=x_temp;
        y_temp1=y_temp;
        num=1;
        for(int i=1;i<6;i++){
            x_temp1+=1;
            y_temp1-=1;
            if(y_temp1<0 || x_temp1>this.width)
                break;
            if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
                num++;
            else
                break;
        }
        //判断左下
        x_temp1=x_temp;
        y_temp1=y_temp;
        for(int i=1;i<6;i++){
            x_temp1-=1;
            y_temp1+=1;
            if(y_temp1>this.height || x_temp1<0)
                break;
            if(this.arrMapShow[x_temp1][y_temp1]==arrvalue)
                num++;
            else
                break;
        }
        if(num==5)
            return true;
        return false;
    }

}
