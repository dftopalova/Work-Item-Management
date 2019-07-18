package com.KSDT.tests.models.common;

import com.KSDT.models.BoardImpl;
import com.KSDT.models.TeamImpl;
import com.KSDT.models.common.Pair;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SizeType;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.StoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Pair_Tests {
    Pair pair;
    Pair pair1;
    String str;
    int key;

    @Before
    public void before(){
        str = "test";
        key = 0;

    }

    @Test
    public void constructor_Should_CreateNewPair() {
        //Arrange, Act
        pair = Pair.create(key, str);

        //Assert
        Assert.assertTrue(pair instanceof Pair);
    }

    @Test
    public void equals_Should_ReturnTrue_WhenTwoPairsAreTheSame() {
        //Arrange, Act
        pair = Pair.create(key, str);
        pair1 = Pair.create(key, str);

        //Assert
        Assert.assertTrue(pair.equals(pair1));
    }

    @Test
    public void equals_Should_ReturnFalse_WhenTwoPairsAreDifferent() {
        //Arrange, Act
        pair = Pair.create(key, str);
        pair1 = Pair.create("test", "test");

        //Assert
        Assert.assertFalse(pair.equals(pair1));
    }

    @Test
    public void equals_Should_ReturnFalse_WhenPassedObjectIsNotPair() {
        //Arrange, Act
        pair = Pair.create(key, str);
        pair1 = Pair.create("test", "test");

        //Assert
        Assert.assertFalse(pair.equals(str));
    }

    @Test
    public void equals_Should_ReturnFirstElement() {
        //Arrange, Act
        pair = Pair.create(key, str);

        //Assert
        Assert.assertEquals(0, pair.getFirst());
    }

    @Test
    public void equals_Should_ReturnSecondElement() {
        //Arrange, Act
        pair = Pair.create(key, str);

        //Assert
        Assert.assertEquals("test", pair.getSecond());
    }

    @Test
    public void equals_Should_ReturnCorrectToString() {
        //Arrange, Act
        pair = Pair.create(key, str);

        StringBuilder strBuild = new StringBuilder();
        strBuild.append("0 test" + System.lineSeparator());

        //Assert
        Assert.assertEquals(strBuild.toString(), pair.toString());
    }



}
