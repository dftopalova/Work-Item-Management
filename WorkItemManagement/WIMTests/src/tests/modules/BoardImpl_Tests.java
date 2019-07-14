package tests.modules;

import com.KSDT.models.BoardImpl;
import com.KSDT.models.PersonImpl;
import com.KSDT.models.TeamImpl;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SizeType;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.StoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class BoardImpl_Tests {
    private WorkItem testItem;
    private Team testTeam;

    @Before
    public void before(){
        testItem=new StoryImpl("test_story",
                StatusType.STORY_DONE,
                "One interesting story",
                PriorityType.MEDIUM, SizeType.MEDIUM);

        testTeam=new TeamImpl("test_team");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_Should_ThrowExceptionWhenNameIsEmpty() {
        //Arrange, Act, Assert
        Board test_board =new BoardImpl("",testTeam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_Should_ThrowExceptionWhenNameLengthIsLessThanMinimum() {
        //Arrange, Act, Assert
        Board test_board =new BoardImpl("d",testTeam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_ThrowExceptionWhenNameLengthIsBiggerThanMaximum() {
        //Arrange, Act, Assert
        Board test_board =new BoardImpl("asdfghjklqwertyui",testTeam);
    }

    @Test
    public void constructor_should_BeSuccessfullWhenNameLengthIsCorrect() {
        //Arrange, Act
        Board test_board =new BoardImpl("test_team",testTeam);

        //Assert
        Assert.assertEquals(test_board.getName(), "test_team");
    }

    @Test
    public void getHistory_Should_ReturnShallowCopy() {
        // Arrange
        Board test_board=new BoardImpl("test_board",testTeam);

        // Act
        List<String> supposedShallowCopy = test_board.getHistory();
        test_board.addToHistory("Changed size of story test_story from medium to low.");

        // Assert
        Assert.assertEquals(0, supposedShallowCopy.size());
    }
}
