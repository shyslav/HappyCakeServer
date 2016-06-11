package com.shyslav.Testing;

import com.shyslav.controller.Main;
import com.shyslav.selectCommands.CasirAction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Shyshkin Vladyslav on 07.06.2016.
 */
public class CasirActionTest {
    @Test
    public void selectNullCategoryTest()
    {
        if(CasirAction.CasirAction()==null)
        {
            Assert.fail("Не верное подключение, ничего не считалось");
        }
    }
    @Test
    public void selectZeroSizeCategoryTest()
    {
        if(CasirAction.CasirAction().size()==0){
            Assert.fail("Лист пустой, в базе пусто");
        }
    }
    @Test
    public void sendMessageCassirToCookTest()
    {
        Assert.assertNotNull(Main.sendToCook());
    }
    @Test
    public void sendMessageErrors()
    {
        String s = Main.sendToCook();
        Assert.assertNotEquals("Ошибка потока",s,"sleep Error");
        Assert.assertNotEquals("Ошибка отправки",s,"SendError");
    }
    @Test
    public void sendMessageNotUsersError()
    {
        String s = Main.sendToCook();
        Assert.assertNotEquals("Некому отправлять",s,"noUsers");
    }
}
