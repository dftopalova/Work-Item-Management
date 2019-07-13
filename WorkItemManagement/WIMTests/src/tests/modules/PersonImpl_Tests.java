package tests.modules;

import com.KSDT.models.PersonImpl;
import com.KSDT.models.contracts.Person;
import org.junit.Assert;
import org.junit.Test;

public class PersonImpl_Tests {

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throwError_when_nameIsEmpty() {
        //Arrange, Act, Assert
        Person test_person =new PersonImpl("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throwError_when_nameLengthLessThanMinimum() {
        //Arrange, Act, Assert
        Person test_person=new PersonImpl("d");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throwError_when_nameLengthBiggerThanMaximum() {
        //Arrange, Act, Assert
        Person test_person=new PersonImpl("asdfghjklqwertyui");
    }

    @Test
    public void constructor_should_beSuccessfull_when_nameLengthIsCorrect() {
        //Arrange, Act
        Person test_person=new PersonImpl("Valentin");

        //Assert
        Assert.assertEquals(test_person.getName(), "Valentin");
    }

}
