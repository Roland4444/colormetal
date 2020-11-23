package ru.com.avs.util.abstractions;

import org.junit.Test;
import ru.com.avs.controller.WaybillJournalController;
import ru.com.avs.model.WeighingView;
import ru.com.avs.util.WayBillUtil;

import java.io.IOException;

import static org.junit.Assert.*;

public class RequestMessageTest {
    WeighingView restored;

    public RequestMessageTest() {

        try {
            restored = WayBillUtil.restoreBytesToWayBill(WaybillJournalController.FileNameDump);
        } catch (IOException e) {
        };
    };


  //  @Test
  //  public void requestMessageTest() {
  //      RequestMessage req = new RequestMessage("такнадор", restored);
  //      assertNotEquals(null, req);
  //      RequestMessage.printIT(req);

  //  }
}