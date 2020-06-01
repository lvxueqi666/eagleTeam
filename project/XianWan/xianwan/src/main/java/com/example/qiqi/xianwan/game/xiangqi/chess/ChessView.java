package com.example.qiqi.xianwan.game.xiangqi.chess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.game.xiangqi.util.Util;

public class ChessView extends View{
    private static final int PHASE_LOADING = 0;
    private static final int PHASE_WAITING = 1;
    private static final int PHASE_THINKING = 2;
    private static final int PHASE_EXITTING = 3;

    private static final int COMPUTER_BLACK = 0;
    private static final int COMPUTER_RED = 1;
    private static final int COMPUTER_NONE = 2;

    private static final int RESP_HUMAN_SINGLE = -2;
    private static final int RESP_HUMAN_BOTH = -1;
    private static final int RESP_CLICK = 0;
    private static final int RESP_ILLEGAL = 1;
    private static final int RESP_MOVE = 2;
    private static final int RESP_MOVE2 = 3;
    private static final int RESP_CAPTURE = 4;
    private static final int RESP_CAPTURE2 = 5;
    private static final int RESP_CHECK = 6;
    private static final int RESP_CHECK2 = 7;
    private static final int RESP_WIN = 8;
    private static final int RESP_DRAW = 9;
    private static final int RESP_LOSS = 10;

    private Bitmap imgBackground, imgXQWLight/*,imgThinking*/;
    private static final String[] IMAGE_NAME = { null, null, null, null, null,
            null, null, null, "rk", "ra", "rb", "rn", "rr", "rc", "rp", null,
            "bk", "ba", "bb", "bn", "br", "bc", "bp", null, };
    private int widthBackground, heightBackground;

    static final int RS_DATA_LEN = 512;

    byte[] rsData = new byte[RS_DATA_LEN];

    byte[] retractData = new byte[RS_DATA_LEN];

    private Position pos = new Position();
    private Search search = new Search(pos, 12);
    private String message;
    private int cursorX, cursorY;
    private int sqSelected, mvLast;
    // Assume FullScreenMode = false
    private int normalWidth = getWidth();
    private int normalHeight = getHeight();

    volatile int phase = PHASE_LOADING;

    private boolean init = false;
    private Bitmap imgBoard, imgSelected, imgSelected2, imgCursor, imgCursor2;
    private Bitmap[] imgPieces = new Bitmap[24];
    private int squareSize, width, height, left, right, top, bottom;
    private Context context;
    private Paint paint = new Paint();

    public ChessView(Context context) {
        super(context);
        imgBackground = BitmapFactory.decodeResource(getResources(),
                R.drawable.background);
        imgXQWLight = BitmapFactory.decodeResource(getResources(),
                R.drawable.xqwlight);
//		imgThinking = BitmapFactory.decodeResource(getResources(),
//				R.drawable.thinking);
        widthBackground = imgBackground.getWidth();
        heightBackground = imgBackground.getHeight();
        this.context = context;
    }

    int moveMode, level;

    void load(byte rsData[], int handicap, int moveMode, int level) {
        this.moveMode = moveMode;
        this.level = level;
        this.rsData = rsData;
        cursorX = cursorY = 7;
        sqSelected = mvLast = 0;

        if (rsData[0] == 0) {
            pos.fromFen(Position.STARTUP_FEN[handicap]);
        } else {
            // Restore Record-Score Data
            pos.clearBoard();
            for (int sq = 0; sq < 256; sq++) {
                int pc = rsData[sq + 256];
                if (pc > 0) {
                    pos.addPiece(sq, pc);
                }
            }
            if (rsData[0] == 2) {
                pos.changeSide();
            }
            pos.setIrrev();
        }
        // Backup Retract Status
        System.arraycopy(rsData, 0, retractData, 0, RS_DATA_LEN);
        // Call "responseMove()" if Computer Moves First
        phase = PHASE_LOADING;
        if (pos.sdPlayer == 0 ? moveMode == COMPUTER_RED
                : moveMode == COMPUTER_BLACK) {
            new Thread() {
                public void run() {
                    while (phase == PHASE_LOADING) {
                        try {
                            sleep(100);
                        } catch (InterruptedException e) {
                            // Ignored
                        }
                    }
                    responseMove();
                }
            }.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawChess(canvas);
        super.onDraw(canvas);
    }

    protected void drawChess(Canvas canvas) {
        if (phase == PHASE_LOADING) {
            // Wait 1 second for resizing
            width = getWidth();
            height = getHeight();
            for (int i = 0; i < 10; i++) {
                if (width != normalWidth || height != normalHeight) {
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // Ignored
                }
                width = getWidth();
                height = getHeight();
            }
            if (!init) {
                init = true;
                // "width" and "height" are Full-Screen values
                squareSize = Math.min(width / 9, height / 10);

                int boardWidth = squareSize * 9;
                int boardHeight = squareSize * 10;
                try {
                    imgBoard = BitmapFactory.decodeResource(getResources(),
                            R.drawable.board);
                    imgSelected = BitmapFactory.decodeResource(getResources(),
                            R.drawable.selected);
                    imgSelected2 = BitmapFactory.decodeResource(getResources(),
                            R.drawable.selected2);
                    imgCursor = BitmapFactory.decodeResource(getResources(),
                            R.drawable.cursor);
                    imgCursor2 = BitmapFactory.decodeResource(getResources(),
                            R.drawable.cursor2);
                    for (int pc = 0; pc < 24; pc++) {
                        if (IMAGE_NAME[pc] == null) {
                            imgPieces[pc] = null;
                        } else {
                            imgPieces[pc] = BitmapFactory.decodeResource(
                                    getResources(),
                                    getResources().getIdentifier(
                                            "" + IMAGE_NAME[pc], "drawable",
                                            context.getPackageName()));
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
                left = (width - boardWidth) / 2;
                top = (height - boardHeight) / 2;
                right = left + boardWidth - 32;
                bottom = top + boardHeight - 32;
            }
            phase = PHASE_WAITING;
        }
        for (int x = 0; x < width; x += widthBackground) {
            for (int y = 0; y < height; y += heightBackground) {
                canvas.drawBitmap(imgBackground, x, y, paint);
            }
        }

        Bitmap mBitmap = Bitmap.createScaledBitmap(imgBoard, right - left + 32,
                bottom - top + 32, false);
        canvas.drawBitmap(mBitmap, left, 0, paint);
        for (int sq = 0; sq < 256; sq++) {
            if (Position.IN_BOARD(sq)) {
                int pc = pos.squares[sq];
                if (pc > 0) {
                    drawSquare(canvas, imgPieces[pc], sq);
                }
            }
        }
        int sqSrc = 0;
        int sqDst = 0;
        if (mvLast > 0) {
            sqSrc = Position.SRC(mvLast);
            sqDst = Position.DST(mvLast);
            drawSquare(canvas, (pos.squares[sqSrc] & 8) == 0 ? imgSelected
                    : imgSelected2, sqSrc);
            drawSquare(canvas, (pos.squares[sqDst] & 8) == 0 ? imgSelected
                    : imgSelected2, sqDst);
        } else if (sqSelected > 0) {
            drawSquare(canvas, (pos.squares[sqSelected] & 8) == 0 ? imgSelected
                    : imgSelected2, sqSelected);
        }
        int sq = Position.COORD_XY(cursorX + Position.FILE_LEFT, cursorY
                + Position.RANK_TOP);
        if (moveMode == COMPUTER_RED) {
            sq = Position.SQUARE_FLIP(sq);
        }
        if (sq == sqSrc || sq == sqDst || sq == sqSelected) {
            drawSquare(canvas, (pos.squares[sq] & 8) == 0 ? imgCursor2
                    : imgCursor, sq);
        } else {
            drawSquare(canvas, (pos.squares[sq] & 8) == 0 ? imgCursor
                    : imgCursor2, sq);
        }
        if (phase == PHASE_THINKING) {
            int x, y;
            if (moveMode == COMPUTER_RED) {
                x = (Position.FILE_X(sqDst) < 8 ? left : right);
                y = (Position.RANK_Y(sqDst) < 8 ? top : bottom);
            } else {
                x = (Position.FILE_X(sqDst) < 8 ? right : left);
                y = (Position.RANK_Y(sqDst) < 8 ? bottom : top);
            }
            //canvas.drawBitmap(imgThinking, x, y, paint);
        } else if (phase == PHASE_EXITTING) {

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction()==MotionEvent.ACTION_DOWN) {

            pointerPressed(event.getX(), event.getY());
        }
        return super.onTouchEvent(event);
    }

    protected void pointerPressed(float x, float y) {

        if (phase == PHASE_THINKING) {
            return;
        }
        cursorX = Util.MIN_MAX(0, ((int) x - left) / squareSize, 8);
        cursorY = Util.MIN_MAX(0, ((int) y - top) / squareSize, 9);
        clickSquare();
        invalidate();

    }

    private void clickSquare() {
        int sq = Position.COORD_XY(cursorX + Position.FILE_LEFT, cursorY
                + Position.RANK_TOP);
        if (moveMode == COMPUTER_RED) {
            sq = Position.SQUARE_FLIP(sq);
        }
        int pc = pos.squares[sq];
        if ((pc & Position.SIDE_TAG(pos.sdPlayer)) != 0) {

            mvLast = 0;
            sqSelected = sq;
        } else {
            if (sqSelected > 0 && addMove(Position.MOVE(sqSelected, sq))
                    && !responseMove()) {
                rsData[0] = 0;
                phase = PHASE_EXITTING;
            }
        }
    }

    private void drawSquare(Canvas canvas, Bitmap bitmap, int sq) {
        int sqFlipped = (moveMode == COMPUTER_RED ? Position.SQUARE_FLIP(sq)
                : sq);
        int sqX = left + (Position.FILE_X(sqFlipped) - Position.FILE_LEFT)
                * squareSize;
        int sqY = top + (Position.RANK_Y(sqFlipped) - Position.RANK_TOP)
                * squareSize;
        if (bitmap != null) {

            Bitmap mBitmap = Bitmap.createScaledBitmap(bitmap, squareSize,squareSize, true);
            canvas.drawBitmap(mBitmap, sqX, sqY, paint);
        }

    }

    /** Player Move Result */
    private boolean getResult() {
        return getResult(moveMode == COMPUTER_NONE ? RESP_HUMAN_BOTH
                : RESP_HUMAN_SINGLE);
    }

    /** Computer Move Result */
    private boolean getResult(int response) {
        if (pos.isMate()) {
            message = (response < 0 ? "祝贺你取得胜利！" : "请再接再厉！");
            return true;
        }
        int vlRep = pos.repStatus(3);
        if (vlRep > 0) {
            vlRep = (response < 0 ? pos.repValue(vlRep) : -pos.repValue(vlRep));
            message = (vlRep > Position.WIN_VALUE ? "长打作负，请不要气馁！"
                    : vlRep < -Position.WIN_VALUE ? "电脑长打作负，祝贺你取得胜利！"
                    : "双方不变作和，辛苦了！");
            return true;
        }
        if (pos.moveNum > 100) {
            message = "超过自然限着作和，辛苦了！";
            return true;
        }
        if (response != RESP_HUMAN_SINGLE) {
            if (response >= 0) {
            }
            // Backup Retract Status
            System.arraycopy(rsData, 0, retractData, 0, RS_DATA_LEN);
            // Backup Record-Score Data
            rsData[0] = (byte) (pos.sdPlayer + 1);
            System.arraycopy(pos.squares, 0, rsData, 256, 256);
        }
        return false;
    }

    private boolean addMove(int mv) {
        if (pos.legalMove(mv)) {
            if (pos.makeMove(mv)) {
                if (pos.captured()) {
                    pos.setIrrev();
                }
                sqSelected = 0;
                mvLast = mv;
                return true;
            }
        }
        return false;
    }

    boolean responseMove() {
        if (getResult()) {
            return false;
        }
        if (moveMode == COMPUTER_NONE) {
            return true;
        }
        phase = PHASE_THINKING;
        invalidate();

        mvLast = search.searchMain(1000 << (level << 1));
        pos.makeMove(mvLast);
        int response = pos.inCheck() ? RESP_CHECK2
                : pos.captured() ? RESP_CAPTURE2 : RESP_MOVE2;
        if (pos.captured()) {
            pos.setIrrev();
        }
        phase = PHASE_WAITING;
        invalidate();

        return !getResult(response);
    }

    void back() {
        if (phase == PHASE_WAITING) {
        } else {
            rsData[0] = 0;
        }
    }

    void retract(int handicap) {
        // Restore Retract Status
        System.arraycopy(retractData, 0, rsData, 0, RS_DATA_LEN);
        load(rsData, handicap, moveMode, level);
        invalidate();

    }

    void about() {
        phase = PHASE_LOADING;
    }
}

