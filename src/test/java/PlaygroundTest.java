import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.List;
import java.util.LinkedList;
import static org.mockito.Mockito.*;


public class PlaygroundTest {

    @Test
    public void invocation() {
        List<String> mock = mock(List.class);

        mock.add("one");
        mock.clear();

        verify(mock).add("one");
    }

    @Test(expected = RuntimeException.class)
    public void stubbing() {
        LinkedList<String> mock = mock(LinkedList.class);

        when(mock.get(0)).thenReturn("first");
        when(mock.get(1)).thenThrow(new RuntimeException());

        Assert.assertEquals("first", mock.get(0));
        System.out.println(mock.get(1));

        verify(mock).get(0);
    }

    @Test
    public void matcher() {
        LinkedList<String> mock = mock(LinkedList.class);

        when(mock.get(anyInt())).thenReturn("test");

        Assert.assertEquals("test", mock.get(0));
        Assert.assertEquals("test", mock.get(100));
        Assert.assertEquals("test", mock.get(987));

        verify(mock, times(3)).get(anyInt());
    }

    @Test
    public void complex() {
        LinkedList<String> mock = mock(LinkedList.class);

        mock.add("once");
        mock.add("once again");

        mock.add("twice");
        mock.add("twice");

        mock.add("three times");
        mock.add("three times");
        mock.add("three times");

        verify(mock).add("once");
        verify(mock, times(1)).add("once");
        verify(mock, times(2)).add("twice");
        verify(mock, times(3)).add("three times");

        verify(mock, never()).add("test");
        verify(mock, atLeastOnce()).add("once");
        verify(mock, atLeastOnce()).add("twice");
        verify(mock, atLeast(2)).add("twice");
        verify(mock, atLeast(2)).add("three times");
        verify(mock, atMost(5)).add("twice");
        verify(mock, atMost(5)).add("three times");

        InOrder order = inOrder(mock);
        order.verify(mock).add("once");
        order.verify(mock).add("once again");

        List<String> firstMock = mock(List.class);
        List<String> secondMock = mock(List.class);

        verifyZeroInteractions(firstMock, secondMock);

    }
 
}
