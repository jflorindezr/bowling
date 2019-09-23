package com.test.app;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BowlingAppIntegrationTest {

    private static final String NORMAL_HITS = "hits.txt";
    private static final String NORMAL_HITS_2 = "hits_2.txt";
    private static final String ALL_FAULTS = "hits_all_F.txt";
    private static final String ALL_ZEROS = "hits_all_zeros.txt";
    private static final String MULTIPLE_PLAYERS = "hits_multiple_players.txt";
    private static final String HITS_PERFECT = "hits_perfect.txt";
    private static final String HITS_WRONG_CHANCES = "hits_wrong_chances.txt";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStream() {
        System.setOut(originalOut);
    }

    @Test
    public void GIVEN_normal_hits_WHEN_processing_score_board_THEN_score_board_is_generated() throws Exception {
        // GIVEN
        String fileName = this.getClass().getClassLoader().getResource(NORMAL_HITS).getPath();

        // WHEN
        BowlingApp.main(new String [] { fileName });

        // THEN
        String scoreBoard = outContent.toString();
        Assert.assertThat(outContent.toString(), Matchers.startsWith("Frame"));
        String [] lines = scoreBoard.split(System.getProperty("line.separator"));
        Assert.assertThat(lines.length, Matchers.equalTo(7));
        Assert.assertThat(lines[0], Matchers.equalTo("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10"));
        Assert.assertThat(lines[1], Matchers.equalTo("Jeff"));
        Assert.assertThat(lines[2], Matchers.equalTo("Pinfalls\t\tX\t7\t/\t9\t0\t\tX\t0\t8\t8\t/\tF\t6\t\tX\t\tX\tX\t8\t1\t"));
        Assert.assertThat(lines[3], Matchers.equalTo("Score\t\t20\t\t39\t\t48\t\t66\t\t74\t\t84\t\t90\t\t120\t\t148\t\t167\t\t"));
        Assert.assertThat(lines[4], Matchers.equalTo("John"));
        Assert.assertThat(lines[5], Matchers.equalTo("Pinfalls\t3\t/\t6\t3\t\tX\t8\t1\t\tX\t\tX\t9\t0\t7\t/\t4\t4\tX\t9\t0\t"));
        Assert.assertThat(lines[6], Matchers.equalTo("Score\t\t16\t\t25\t\t44\t\t53\t\t82\t\t101\t\t110\t\t124\t\t132\t\t151\t\t"));
    }

    @Test
    public void GIVEN_normal_hits_2_WHEN_processing_score_board_THEN_score_board_is_generated() throws Exception {
        // GIVEN
        String fileName = this.getClass().getClassLoader().getResource(NORMAL_HITS_2).getPath();

        // WHEN
        BowlingApp.main(new String [] { fileName });

        // THEN
        String scoreBoard = outContent.toString();
        String [] lines = scoreBoard.split(System.getProperty("line.separator"));
        Assert.assertThat(lines.length, Matchers.equalTo(7));
        Assert.assertThat(lines[0], Matchers.equalTo("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10"));
        Assert.assertThat(lines[1], Matchers.equalTo("Jeff"));
        Assert.assertThat(lines[2], Matchers.equalTo("Pinfalls\t8\t/\t7\t/\t3\t4\t\tX\t2\t/\t\tX\t\tX\t8\t0\t\tX\t8\t/\t9\t"));
        Assert.assertThat(lines[3], Matchers.equalTo("Score\t\t17\t\t30\t\t37\t\t57\t\t77\t\t105\t\t123\t\t131\t\t151\t\t170\t\t"));
        Assert.assertThat(lines[4], Matchers.equalTo("John"));
        Assert.assertThat(lines[5], Matchers.equalTo("Pinfalls\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t3\t"));
        Assert.assertThat(lines[6], Matchers.equalTo("Score\t\t6\t\t12\t\t18\t\t24\t\t30\t\t36\t\t42\t\t48\t\t54\t\t60\t\t"));
    }

    @Test
    public void GIVEN_all_faults_WHEN_processing_score_board_THEN_score_board_is_generated() throws Exception {
        // GIVEN
        String fileName = this.getClass().getClassLoader().getResource(ALL_FAULTS).getPath();

        // WHEN
        BowlingApp.main(new String [] { fileName });

        // THEN
        String scoreBoard = outContent.toString();
        String [] lines = scoreBoard.split(System.getProperty("line.separator"));
        Assert.assertThat(lines.length, Matchers.equalTo(4));
        Assert.assertThat(lines[0], Matchers.equalTo("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10"));
        Assert.assertThat(lines[1], Matchers.equalTo("Jeff"));
        Assert.assertThat(lines[2], Matchers.equalTo("Pinfalls\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\t"));
        Assert.assertThat(lines[3], Matchers.equalTo("Score\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t"));
    }

    @Test
    public void GIVEN_all_zeros_WHEN_processing_score_board_THEN_score_board_is_generated() throws Exception {
        // GIVEN
        String fileName = this.getClass().getClassLoader().getResource(ALL_ZEROS).getPath();

        // WHEN
        BowlingApp.main(new String [] { fileName });

        // THEN
        String scoreBoard = outContent.toString();
        String [] lines = scoreBoard.split(System.getProperty("line.separator"));
        Assert.assertThat(lines.length, Matchers.equalTo(4));
        Assert.assertThat(lines[0], Matchers.equalTo("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10"));
        Assert.assertThat(lines[1], Matchers.equalTo("Jeff"));
        Assert.assertThat(lines[2], Matchers.equalTo("Pinfalls\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t"));
        Assert.assertThat(lines[3], Matchers.equalTo("Score\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t"));
    }

    @Test
    public void GIVEN_multiple_players_WHEN_processing_score_board_THEN_score_board_is_generated() throws Exception {
        // GIVEN
        String fileName = this.getClass().getClassLoader().getResource(MULTIPLE_PLAYERS).getPath();

        // WHEN
        BowlingApp.main(new String [] { fileName });

        // THEN
        String scoreBoard = outContent.toString();
        String [] lines = scoreBoard.split(System.getProperty("line.separator"));
        Assert.assertThat(lines.length, Matchers.equalTo(13));
        Assert.assertThat(lines[0], Matchers.equalTo("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10"));
        Assert.assertThat(lines[1], Matchers.equalTo("Jeff"));
        Assert.assertThat(lines[2], Matchers.equalTo("Pinfalls\t\tX\t7\t/\t7\t/\t7\t/\t7\t/\t7\t/\t7\t/\t7\t/\t7\t/\t7\t/\t4\t"));
        Assert.assertThat(lines[3], Matchers.equalTo("Score\t\t20\t\t37\t\t54\t\t71\t\t88\t\t105\t\t122\t\t139\t\t156\t\t170\t\t"));
        Assert.assertThat(lines[4], Matchers.equalTo("John"));
        Assert.assertThat(lines[5], Matchers.equalTo("Pinfalls\t3\t/\t3\t/\t3\t/\t3\t/\t3\t/\t3\t/\t3\t/\t3\t/\t3\t/\tX\t7\t2\t"));
        Assert.assertThat(lines[6], Matchers.equalTo("Score\t\t13\t\t26\t\t39\t\t52\t\t65\t\t78\t\t91\t\t104\t\t124\t\t143\t\t"));
        Assert.assertThat(lines[7], Matchers.equalTo("Matt"));
        Assert.assertThat(lines[8], Matchers.equalTo("Pinfalls\t2\t5\t2\t5\t2\t5\t2\t5\t2\t5\t2\t5\t2\t5\t2\t5\t2\t5\t2\t5\t"));
        Assert.assertThat(lines[9], Matchers.equalTo("Score\t\t7\t\t14\t\t21\t\t28\t\t35\t\t42\t\t49\t\t56\t\t63\t\t70\t\t"));
        Assert.assertThat(lines[10], Matchers.equalTo("Tom"));
        Assert.assertThat(lines[11], Matchers.equalTo("Pinfalls\t4\t/\t4\t/\t4\t/\t4\t/\t4\t/\t4\t/\t4\t/\t4\t/\t4\t/\t4\t/\t"));
        Assert.assertThat(lines[12], Matchers.equalTo("Score\t\t14\t\t28\t\t42\t\t56\t\t70\t\t84\t\t98\t\t112\t\t126\t\t136\t\t"));
    }

    @Test
    public void GIVEN_perfect_WHEN_processing_score_board_THEN_score_board_is_generated() throws Exception {
        // GIVEN
        String fileName = this.getClass().getClassLoader().getResource(HITS_PERFECT).getPath();

        // WHEN
        BowlingApp.main(new String [] { fileName });

        // THEN
        String scoreBoard = outContent.toString();
        String [] lines = scoreBoard.split(System.getProperty("line.separator"));
        Assert.assertThat(lines.length, Matchers.equalTo(4));
        Assert.assertThat(lines[0], Matchers.equalTo("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10"));
        Assert.assertThat(lines[1], Matchers.equalTo("Carl"));
        Assert.assertThat(lines[2], Matchers.equalTo("Pinfalls\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\tX\tX\tX\t"));
        Assert.assertThat(lines[3], Matchers.equalTo("Score\t\t30\t\t60\t\t90\t\t120\t\t150\t\t180\t\t210\t\t240\t\t270\t\t300\t\t"));
    }

    @Test(expected = Exception.class)
    public void GIVEN_wrong_chances_WHEN_processing_score_board_THEN_score_board_is_generated() throws Exception {
        // GIVEN
        String fileName = this.getClass().getClassLoader().getResource(HITS_WRONG_CHANCES).getPath();

        // WHEN
        BowlingApp.main(new String [] { fileName });

        // THEN
    }

}
