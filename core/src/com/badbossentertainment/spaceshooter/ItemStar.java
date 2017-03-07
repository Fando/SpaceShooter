package com.badbossentertainment.spaceshooter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alex on 03/07/2015.
 */
public class ItemStar extends Item {
    public static final float STAR_SIZE_OUTER = 20;
    public static final float STAR_SIZE_INNER = 10;
    public static final Color STAR_COLOR = Color.YELLOW;
    public static final SoundName PICK_UP_SOUND = SoundName.Hit7;
    private int multiplier = 1;
    /**
     *
     * @param x
     * @param y
     * @param multiplier the amount that the default star-value is multiplied by to determine the value of a star. multiplier affects star size as well.
     *
     */
    public ItemStar(float x, float y, int multiplier) {
        super(x, y, STAR_SIZE_OUTER*2*multiplier, STAR_SIZE_OUTER*2*multiplier);
        super.setColor(STAR_COLOR);
        super.setOrientInDirectionOfVelocity(false);
        super.setOrientation(MathUtils.random(0, 359));
        super.setPickUpSound(PICK_UP_SOUND);
        this.multiplier = multiplier;
    }

    /**
     * Draw a star by drawing two filled squares, one of which is rotated by 45 deg.
     * @param sr
     */
    @Override
    public void render(ShapeRenderer sr, SpriteBatch batch) {
        float scale = getPickUpAnimationScale();
        if (scale==0) scale = 1;

//        sr.getTransformMatrix().translate(getX(), getY(), 0).rotate(0, 0, 1, 0);
//        sr.setColor(Color.RED);
//        sr.rect(0, 0, getWidth(), getHeight());
//        sr.identity();

        sr.getTransformMatrix().translate(getCenterX(), getCenterY(), 0);//.rotate(0, 0, 1, getOrientation());
        sr.setColor(getColor());
        drawStar(sr, 5, new Vector2(0,0), STAR_SIZE_OUTER * this.multiplier * scale, STAR_SIZE_INNER * this.multiplier * scale);
        sr.identity();
    }

    public void drawStar(ShapeRenderer sr, int arms, Vector2 center, float rOuter, float rInner)
    {
        double angle = Math.PI / arms;

        Vector2 pInit = new Vector2();
        Vector2 pPrev = new Vector2();
        for (int i = 0; i < 2 * arms; i++)
        {
            float r = (i & 1) == 0 ? rOuter : rInner;
            Vector2 p = new Vector2(center.x + (float)Math.cos(getOrientation() + i * angle) * r, center.y + (float)Math.sin(getOrientation() + i * angle) * r); // getOrientation() is added to offset the star angle randomly
            if (i == 0) {
                pInit.set(p);
                pPrev.set(p);
            } else {
                sr.line(pPrev.x, pPrev.y, p.x, p.y);
                pPrev.set(p);
            }
        }
        sr.line(pPrev.x, pPrev.y, pInit.x, pInit.y);
    }
}
