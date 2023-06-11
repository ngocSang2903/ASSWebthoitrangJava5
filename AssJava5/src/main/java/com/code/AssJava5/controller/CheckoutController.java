package com.code.AssJava5.controller;


import com.code.AssJava5.dto.MailInfo;
import com.code.AssJava5.entity.*;
import com.code.AssJava5.repositories.OrderDetailRepo;
import com.code.AssJava5.repositories.OrderRepo;
import com.code.AssJava5.repositories.ProductRepo;
import com.code.AssJava5.service.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class CheckoutController {

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    OrderDetailRepo orderDetailRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    SessionService sessionService;

    @Autowired
    MailerService mailerService;

    @RequestMapping("/checkout")
    public String index(Model model){
        model.addAttribute("cart",cartService.getItems());
        model.addAttribute("countcart",cartService.getCount());
        model.addAttribute("amount",cartService.getAmount());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String sucsess(Model model, @RequestParam("phone") String phone, @RequestParam("address") String address,
                          RedirectAttributes redirectAttributes) throws MessagingException {
        Order order =new Order();
        Account user=(Account) sessionService.get("user");
        order.setCreateDate(new Date());
        order.setAddress(address);
        order.setPhone(phone);
        order.setAccount(user);
        orderRepo.save(order);

        for (Item item:cartService.getItems()){
            Order orderid=orderRepo.getReferenceById(order.getId());
            Product productid=productRepo.getReferenceById(item.getId());
            orderDetailRepo.save(new OrderDetail(item.getPrice(), item.getQty(), productid,orderid));
        }


        for (Item item:cartService.getItems()) {
            String donhang= item.getId()+","+item.getName()+","+item.getQty()+","+item.getPrice();
            System.out.println(donhang);
        }

        Date currentDate = new Date();

        // Định dạng ngày giờ
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateFormat.format(currentDate);

        String body = "<html><body>";
        body += "<h4>Kính gửi Anh/chị "+user.getFullname()+"</h4>";
        body += "<h4>Cảm ơn anh/chị đã mua hàng tại Assjava 5, Chúng tôi cảm thấy may mắn khi được phục vụ anh/chị." +
                " Sau đây là hóa đơn chi tiết về đơn hàng </h4>";
        body += "<h3>Hóa đơn tạo lúc : "+formattedDateTime+"</h3>";
        body += "<h4 style='text:bold'>Số hóa đơn :"+order.getId()+" ,Khách hàng: "+user.getFullname()+"</h4>";
        body += "<table border='1'>";
        body += "<tr><th>Id</th><th>Name</th><th>Qty</th><th>Price</th></tr>";
        for (Item item : cartService.getItems()) {
            body+= "<tr><td>" + item.getId() + "</td><td>" + item.getName() + "</td><td>" + item.getQty() + "</td><td>" + item.getPrice() + "</td></tr>";
        }
        body += "<tr>TỔNG: "+cartService.getAmount()+"</tr>";
        body += "</table>";
        body += "<h4>Xin cảm ơn quý khách</h4>";
        body += "</body></html>";

        String subject="Thông tin chi tiết mua hàng";
        String email=user.getEmail();

        mailerService.send(new MailInfo(email,subject,body));


        cartService.getItems().clear();
        redirectAttributes.addFlashAttribute("Messges","Buy success");
        return "redirect:/checkout";
    }

}
