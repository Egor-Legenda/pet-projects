package backend.academy.my_project.tests.utilitsTests;

import static org.assertj.core.api.Assertions.assertThat;

import backend.academy.my_project.utilits.ColorsBlocks;
import backend.academy.my_project.utilits.Variables;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VariablesTest {

    @Test
    @DisplayName("Should return correct value for WALL")
    public void testWallValue() {
        assertThat(ColorsBlocks.BLACK_BLOCK.code()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should return correct value for PASSAGE")
    public void testPassageValue() {
        assertThat(ColorsBlocks.WHITE_BLOCK.code()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should return correct value for SWAMP")
    public void testSwampValue() {
        assertThat(ColorsBlocks.GREEN_BLOCK.code()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should return correct value for THRESHOLD_ONE")
    public void testThresholdOneValue() {
        assertThat(Variables.THRESHOLD_ONE.variable()).isEqualTo(8);
    }

    @Test
    @DisplayName("Should return correct value for THRESHOLD_TWO")
    public void testThresholdTwoValue() {
        assertThat(Variables.THRESHOLD_TWO.variable()).isEqualTo(9);
    }

    @Test
    @DisplayName("Should return correct value for BOUND")
    public void testBoundValue() {
        assertThat(Variables.BOUND.variable()).isEqualTo(10);
    }

    @Test
    @DisplayName("Should return correct value for COIN")
    public void testCoinValue() {
        assertThat(ColorsBlocks.CYAN_BLOCK.code()).isEqualTo(3);
    }
}
