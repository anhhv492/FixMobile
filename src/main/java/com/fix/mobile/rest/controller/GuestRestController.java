package com.fix.mobile.rest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fix.mobile.dto.AccountDTO;
import com.fix.mobile.dto.ColorProductResponDTO;
import com.fix.mobile.dto.ProductDetailDTO;
import com.fix.mobile.dto.ProductResponDTO;
import com.fix.mobile.entity.*;
import com.fix.mobile.repository.SaleRepository;
import com.fix.mobile.service.*;
import com.fix.mobile.utils.UserName;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping(value="/rest/guest")
public class GuestRestController {
    Logger logger = Logger.getLogger(GuestRestController.class);
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AccessoryService accessoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ImayProductService imayProductService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private SaleService saleService;

    @Autowired
    private  CapacityService capacityService;

    @Autowired
    private RamService ramService;

    @Autowired
    private ColorService colorService;

    @Autowired
    sendMailService mailService;

    @Autowired
    SaleService saleSV;
    
    Order order = null;
    Account account = null;

    private List<BigDecimal> getMaxMinPriceAccessory(){
        return accessoryService.getMinMaxPrice();
    }
    @GetMapping("/getAccount")
    public Account getAccountActive() {
        Account account = accountService.findByUsername(UserName.getUserName());
        return account;
    }
    @GetMapping("/category/getAll")
    public List<Category> getAll(){
        return categoryService.findAllBybStatus();
    }
    //find category by accessory
    @GetMapping("/cate")
    public List<Category> findByCate(){
        return categoryService.findByType();
    }
    @GetMapping("/imei/amount/{id}")
    public Integer getAmountImei(@PathVariable("id") Integer id){
        Product product = productService.findById(id).get();
        List<ImayProduct> imeis= imayProductService.findByProductAndStatus(product,1);
        return imeis.size();
    }

    @GetMapping("/productCount")
    public List<ProductResponDTO> productCount(){
        return productService.getProductCount();
    }

    @GetMapping("/findByPriceExits")
    public List<ProductResponDTO> findByPriceExits(){
        return productService.findByPriceExits();
    }
    @GetMapping("/accessory/amount/{id}")
    public Integer getAmountAccessory(@PathVariable("id") Integer id){
        Accessory accessory = accessoryService.findById(id).get();
        return accessory.getQuantity();
    }
    //find accessory by id
    @GetMapping(value="/accessory/{id}")
    public Accessory findById(@PathVariable("id") Integer id){
        Optional<Accessory> accessory = accessoryService.findById(id);
        if(accessory.isPresent()){
            return accessory.get();
        }
        return null;
    }

    //find product by id
    @GetMapping(value="/product/{id}")
    public Product findProductById(@PathVariable("id") Integer id){
        Optional<Product> product = productService.findById(id);
        if(product.isPresent()){
            System.out.println(product.get().getName());
            return product.get();
        }
        return null;
    }
    //find accessory by category
    @GetMapping("/accessory/cate-access/{id}")
    public List<Accessory> findByCateAccessId(@PathVariable("id") Integer id){
        Optional<Category> cate = categoryService.findById(id);
        if(cate.isEmpty()){
            return null;
        }
        List<Accessory> accessories = accessoryService.findByCategoryAndStatus(cate);
        for (int i = 0; i < accessories.size(); i++) {
            if(accessories.get(i).getQuantity() == 0){
                accessories.remove(i);
            }
        }
        return accessories;
    }
    @GetMapping("/product/cate-product/{id}")
    public List<ProductResponDTO> findByCateProductId(@PathVariable("id") Integer id) throws Exception {
        Optional<Category> cate = categoryService.findById(id);
        if(cate.isEmpty()){
            return null;
        }
        List<ProductResponDTO> productResponDTOList = productService.findByCategoryAndStatus(cate.get().getIdCategory());
        if(productResponDTOList == null) throw new Exception("Product not found");
//        for (int i = 0; i < productResponDTOList.size(); i++) {
//            List<ImayProduct> imayProducts = imayProductService.findByProductAndStatus(productResponDTOList.get(i),1);
//            if(imayProducts.size() == 0){
//                productResponDTOList.remove(i);
//            }
//        }
        return productResponDTOList;
    }
    @PostMapping("/order/add")
    public Order order(@RequestBody Order order){
        account = accountService.findByUsername(UserName.getUserName());
        if(order.getAddress()==null||account==null){
            return null;
        }
        Date date = new Date();
        this.order = order;
        order.setCreateDate(date);
        order.setAccount(account);

        orderService.save(order);
        logger.info("-- Order: "+order.getIdOrder());
        return order;
    }
    @PostMapping("/order/detail/add")
    public JsonNode cartItems(@RequestBody JsonNode carts){
        account = accountService.findByUsername(UserName.getUserName());
        OrderDetail orderDetail;
        BigDecimal price =null;
        BigDecimal priceSale =null;
        for (int i=0;i<carts.size();i++){
            if(carts.get(i).get("qty").asInt()<=0){
                return null;
            }else{
                orderDetail = new OrderDetail();
                if(carts.get(i).get("idAccessory")!=null){
                    Optional<Accessory> accessory = accessoryService.findById(carts.get(i).get("idAccessory").asInt());
                    if(accessory.isPresent()){
                        orderDetail.setAccessory(accessory.get());
                        orderDetail.setOrder(order);
                        orderDetail.setStatus(0);
                        orderDetail.setQuantity(carts.get(i).get("qty").asInt());
                        price = new BigDecimal(carts.get(i).get("price").asDouble());
                        orderDetail.setPrice(price);
                        priceSale= new BigDecimal(carts.get(i).get("priceSale").asDouble());
                        orderDetail.setPriceSale(priceSale);
                        if(carts.get(i).get("idSale")==null){
                            orderDetail.setIdSale(null);
                        }else{
                            System.out.println("hi2");
                            orderDetail.setIdSale(carts.get(i).get("idSale").asInt());
                            Sale updatequantity= saleService.findByid(carts.get(i).get("idSale").asInt());
                            updatequantity.setQuantity(updatequantity.getQuantity()-1);
                            saleService.updateQuantity(updatequantity);
                        }
                        orderDetailService.save(orderDetail);
                    }
                } else if (carts.get(i).get("idProduct")!=null){
                    Optional<Product> product = productService.findById(carts.get(i).get("idProduct").asInt());
//                    List<ImayProduct> imayProducts = imayProductService.findByProductAndStatus(product.get(),1);
                    if(product.isPresent()){
                        orderDetail.setProduct(product.get());
                        orderDetail.setOrder(order);
                        orderDetail.setStatus(0);
                        orderDetail.setQuantity(carts.get(i).get("qty").asInt());
                        price = new BigDecimal(carts.get(i).get("price").asDouble());
                        orderDetail.setPrice(price);
                        priceSale= new BigDecimal(carts.get(i).get("priceSale").asDouble());
                        orderDetail.setPriceSale(priceSale);
                        if(carts.get(i).get("idSale")==null){
                            orderDetail.setIdSale(null);
                        }else{
                            orderDetail.setIdSale(carts.get(i).get("idSale").asInt());
                            Sale updatequantity= saleService.findByid(carts.get(i).get("idSale").asInt());
                            updatequantity.setQuantity(updatequantity.getQuantity()-1);
                            saleService.updateQuantity(updatequantity);
                        }
                        orderDetailService.save(orderDetail);
//                        for (int j = 0; j < carts.get(i).get("qty").asInt(); j++) {
//                            imayProducts.get(j).setOrderDetail(orderDetail);
//                            imayProducts.get(j).setStatus(0);
//                            imayProductService.update(imayProducts.get(j),imayProducts.get(j).getIdImay());
//                        }
                    }
                }
            }
        }
        logger.info("-- OrderDetail success: "+account.getUsername());
        return carts;
    }



//    @GetMapping("/cart/sale")
//    public List<Sale> getSaleByAccount(@PathVariable("id") Integer id){
//        List<Sale> sales = saleService.findAllByDate();
//        return sales;
//    }
    @GetMapping(value ="/findByProductCode/{productCode}")
    public Optional<Product> findByProductCode(@PathVariable("productCode") Integer productCode) {
        Optional<Product> product = productService.findById(productCode);

        return Optional.of(product.get());
    }



    @GetMapping("/getAllCapacity")
    public List<Capacity> getAllCapacity(){
        return capacityService.findAll();
    }

    @GetMapping("/getAllRam")
    public List<Ram> getAllRam(){
        return ramService.getALL();
    }

    @GetMapping("/getAllColor")
    public List<Color> getAllColor(){
        return colorService.findAll();
    }


    @GetMapping("/getProductByNameAndCapacityAndColor")
    public List<Product> getProduct(@RequestParam("name") String name,
                              @RequestParam("capacity") Integer capacity,
                              @RequestParam("color") Integer color){
        return productService.findByNameAndCapacityAndColor(name, capacity, color);
    }

    @GetMapping("/getTop4")
    public List<Accessory> getTop4(){
        return accessoryService.getTop4();
    }

    @GetMapping("/getOneAccessory")
    public Accessory getOneAccessory(@RequestParam("id") Integer id){
        return accessoryService.findById(id).orElse(null);
    }

    @GetMapping("/getColorProductByName")
    public List<ColorProductResponDTO> getColorProductByName(@RequestParam("name") String name){
        return productService.getColorProductByName(name);
    }

    @RequestMapping("/sale/getbigsale")
    public Sale getBigSale( @RequestParam(name="money") String money,
                            @RequestParam(name="idPrd") Integer idPrd,
                            @RequestParam(name="idAcsr") Integer idAcsr){
        BigDecimal moneySale;
        String userName;
        account = accountService.findByUsername(UserName.getUserName());
        if(null==account){
            userName=null;
        }else{
            userName = account.getUsername();
        }
        if( 0 == money.length() || "undefined".equals(money) ||"".equals(money)){
            moneySale=null;
        }else{
            moneySale = new BigDecimal(Double.valueOf(money));
        }
        if( 0==idPrd ){
            idPrd=null;
        }
        if( 0 == idAcsr){
            idAcsr=null;
        }
//        System.out.println(saleSV.getBigSale(userName,moneySale,idPrdSale,idAcsrSale));
        return saleSV.getBigSale(userName,moneySale,idPrd,idAcsr);
    }

    @GetMapping("/product/getAll")
    public List<Product> findAll(){
        return productService.findAll();
    }

    @GetMapping(value ="/product/findByProductCode")
    public Optional<Product> findByProductCodeDetail(@RequestParam("id") Integer productCode) {
        Optional<Product> product = null;
        if (productCode == null) ;
        product = productService.findById(productCode);
        return product;
    }

    @RequestMapping(value = "/product/findProductByPrice",method = RequestMethod.GET)
    public List<Product> findProductByPrice(){
        List<Product> listproduct = productService.findProductByPrices();
        if(listproduct.isEmpty()){
            return  null;
        }
        return listproduct;
    }

    @GetMapping("/accounts/updatePasswordMail/{email}")
    public void updatePasswordMail (@PathVariable("email")String email, Model model){
        try {
            if(email != null){
                mailService.SendEmailChangePass("top1zukavietnam@gmail.com","kzbtzovffrqbkonf",email);
            }else;
        }catch (Exception e){
            e.getMessage();
        }
    }

    private List<Accessory> checkListAccessory( List<Accessory> listcheck, Accessory check){
        if(listcheck.size()!=0){
            boolean kT= true;
            for (int i=0;i<listcheck.size();i++){
                if(listcheck.get(i).getIdAccessory()==check.getIdAccessory()){
                    kT = false;
                }
            }
            if(kT==true){
                listcheck.add(check);
            }
        }
        else{
            listcheck.add(check);
        }
        return listcheck;
    }

    private List<Accessory> sortAccessory(List<Accessory> listASSR,Integer check) {
//		BigDecimal a = new BigDecimal(String.valueOf(listASSR.get(0).getPrice()));
        if(listASSR.size()==0){
            return listASSR;
        }else {
            Comparator<Accessory> MinMax = new Comparator<Accessory>() {
                @Override
                public int compare(Accessory o1, Accessory o2) {
                    return o1.getPrice().compareTo(o2.getPrice());
                }
            };
            Comparator<Accessory> MaxMin = new Comparator<Accessory>() {
                @Override
                public int compare(Accessory o1, Accessory o2) {
                    return o2.getPrice().compareTo(o1.getPrice());
                }
            };
            if(check == 0){
                Collections.sort(listASSR, MinMax);
            }else{
                Collections.sort(listASSR, MaxMin);
            }
            return listASSR;
        }
    }

    @RequestMapping("/accessory/findaccessory/{page}")
    public Page<Accessory> findAccessory(
            @PathVariable ("page") Integer page,
            @RequestBody JsonNode findProcuctAll
    ) {
        Pageable paging = PageRequest.of(page, 9);
        BigDecimal priceMin;
        BigDecimal priceMax;
        if(String.valueOf(findProcuctAll.get("priceSalemin")).replaceAll("\"","").equals("")){
            priceMin = new BigDecimal(String.valueOf(getMaxMinPriceAccessory().get(0)));
        }else {
            priceMin = new BigDecimal(String.valueOf(findProcuctAll.get("priceSalemin")).replaceAll("\"", ""));
        }
        if(String.valueOf(findProcuctAll.get("priceSalemax")).replaceAll("\"","").equals("")){
            priceMax = new BigDecimal(String.valueOf(getMaxMinPriceAccessory().get(1)));
        }else {
            priceMax = new BigDecimal(String.valueOf(findProcuctAll.get("priceSalemax")).replaceAll("\"",""));
        }
        List<Accessory> listASSR = accessoryService.findAccessory();
        List<Accessory> listFindAccessory = new ArrayList<>();
        JsonNode listIdCategory = findProcuctAll.get("idCategory");
        JsonNode listIdColer = findProcuctAll.get("idColer");
        Integer sortMinMax = findProcuctAll.get("sortMinMax").asInt();
        if(listASSR.size()!=0){
            for (int i = 0; i < listASSR.size(); i++) {
                if (
                        listIdCategory.size() == 0 &&
                                listIdColer.size() == 0
                ) {
                    if (listASSR.get(i).getName().toLowerCase().contains(String.valueOf(findProcuctAll.get("search")).replaceAll("\"", "").toLowerCase())
                            && listASSR.get(i).getPrice().compareTo(priceMin) >= 0
                            && listASSR.get(i).getPrice().compareTo(priceMax) <= 0
                    ) {
                        listFindAccessory = checkListAccessory(listFindAccessory, listASSR.get(i));
                    }
                } else {
                    if (listIdCategory.size() != 0) {
                        for (int y = 0; y < listIdCategory.size(); y++) {
                            if (listIdCategory.get(y).asInt() == listASSR.get(i).getCategory().getIdCategory()
                                    && listASSR.get(i).getPrice().compareTo(priceMin) >= 0
                                    && listASSR.get(i).getPrice().compareTo(priceMax) <= 0
                                    && listASSR.get(i).getName().toLowerCase().contains(String.valueOf(findProcuctAll.get("search")).replaceAll("\"", "").toLowerCase())
                            ) {
                                listFindAccessory = checkListAccessory(listFindAccessory, listASSR.get(i));
                            }
                        }
                    }

                    if (listIdColer.size() != 0) {
                        for (int y = 0; y < listIdColer.size(); y++) {
                            if (listIdColer.get(y).asText() == listASSR.get(i).getColor()
                                    && listASSR.get(i).getPrice().compareTo(priceMin) >= 0
                                    && listASSR.get(i).getPrice().compareTo(priceMax) <= 0
                                    && listASSR.get(i).getName().toLowerCase().contains(String.valueOf(findProcuctAll.get("search")).replaceAll("\"", "").toLowerCase())
                            ) {
                                listFindAccessory = checkListAccessory(listFindAccessory, listASSR.get(i));
                            }
                        }
                    }
                }
            }

        }
        Page<Accessory> pageFindAccessoryAll = new PageImpl<>(sortAccessory(listFindAccessory,sortMinMax), paging, listFindAccessory.size());
        return pageFindAccessoryAll;
    }

    private List<Product> sortProduct(List<Product> listPRD,Integer check) {
//		BigDecimal a = new BigDecimal(String.valueOf(listPRD.get(0).getPrice()));
        if(listPRD.size()==0){
            return listPRD;
        }else {
            Comparator<Product>  MinMax = new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o1.getPrice().compareTo(o2.getPrice());
                }
            };
            Comparator<Product> MaxMin = new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o2.getPrice().compareTo(o1.getPrice());
                }
            };
            if(check == 0){
                Collections.sort(listPRD, MinMax);
            }else{
                Collections.sort(listPRD, MaxMin);
            }
            return listPRD;
        }
    }

    private List<Product> checkListProduct( List<Product> listcheck, Product check){
        if(listcheck.size()!=0){
            boolean kT= true;
            for (int i=0;i<listcheck.size();i++){
                if(listcheck.get(i).getIdProduct()==check.getIdProduct()){
                    kT = false;
                }
            }
            if(kT==true){
                listcheck.add(check);
            }
        }
        else{
            listcheck.add(check);
        }
        return listcheck;
    }

    @RequestMapping("/product/findproduct/{page}")
    public Page<Product> findProduct(
            @PathVariable ("page") Integer page,
            @RequestBody JsonNode findProcuctAll
    ) {
        Pageable paging = PageRequest.of(page, 4);
        List<Product> listPrd = productService.findProduct();
        List<Product> listFindProduct = new ArrayList<>();
        JsonNode listIdCategory = findProcuctAll.get("idCategory");
        JsonNode listIdRam = findProcuctAll.get("idRam");
        JsonNode listIdColer = findProcuctAll.get("idColer");
        JsonNode listIdCapacity = findProcuctAll.get("idCapacity");
        Integer sortMinMax = findProcuctAll.get("sortMinMax").asInt();
        System.out.println(listPrd.size());
        System.out.println(String.valueOf(findProcuctAll.get("search")).replaceAll("\"","")+"hihihi");
        if(listPrd.size()!=0) {
            for (int i = 0; i < listPrd.size(); i++) {
                if (
                        listIdCategory.size() == 0 &&
                                listIdRam.size() == 0 &&
                                listIdCapacity.size() == 0 &&
                                listIdColer.size() == 0
                ) {
                    if (listPrd.get(i).getName().toLowerCase().contains(String.valueOf(findProcuctAll.get("search")).replaceAll("\"", "").toLowerCase())
                    ) {
                        listFindProduct = checkListProduct(listFindProduct, listPrd.get(i));
                    }
                } else {
                    if (listIdCategory.size() != 0) {
                        for (int y = 0; y < listIdCategory.size(); y++) {
                            if (listIdCategory.get(y).asInt() == listPrd.get(i).getCategory().getIdCategory()
                                    && listPrd.get(i).getName().toLowerCase().contains(String.valueOf(findProcuctAll.get("search")).replaceAll("\"", "").toLowerCase())
                            ) {
                                listFindProduct = checkListProduct(listFindProduct, listPrd.get(i));
                            }
                        }
                    }

                    if (listIdRam.size() != 0) {
                        for (int y = 0; y < listIdRam.size(); y++) {
                            if (listIdRam.get(y).asInt() == listPrd.get(i).getRam().getIdRam()
                                    && listPrd.get(i).getName().toLowerCase().contains(String.valueOf(findProcuctAll.get("search")).replaceAll("\"", "").toLowerCase())
                            ) {
                                listFindProduct = checkListProduct(listFindProduct, listPrd.get(i));
                            }
                        }
                    }

                    if (listIdCapacity.size() != 0) {
                        for (int y = 0; y < listIdCapacity.size(); y++) {
                            if (listIdCapacity.get(y).asInt() == listPrd.get(i).getCapacity().getIdCapacity()
                                    && listPrd.get(i).getName().toLowerCase().contains(String.valueOf(findProcuctAll.get("search")).replaceAll("\"", "").toLowerCase())
                            ) {
                                listFindProduct = checkListProduct(listFindProduct, listPrd.get(i));
                            }
                        }
                    }

                    if (listIdColer.size() != 0) {
                        for (int y = 0; y < listIdColer.size(); y++) {
                            if (listIdColer.get(y).asInt() == listPrd.get(i).getColor().getIdColor()
                                    && listPrd.get(i).getName().toLowerCase().contains(String.valueOf(findProcuctAll.get("search")).replaceAll("\"", "").toLowerCase())
                            ) {
                                listFindProduct = checkListProduct(listFindProduct, listPrd.get(i));
                            }
                        }
                    }
                }
            }
        }
        Page<Product> pageFindProductAll = new PageImpl<>(sortProduct(listFindProduct,sortMinMax), paging, listFindProduct.size());
        System.out.println(pageFindProductAll);
        return pageFindProductAll;
    }

    @RequestMapping("/product/detailproduct/{idcate}")
    public ProductDetailDTO getDetailProduct(
            @PathVariable("idcate")Integer id
    ){
        List<Ram> listRam=new ArrayList<>();
        List<Capacity> listCapa=new ArrayList<>();
        List<Color> listColor=new ArrayList<>();
        for (int x: productService.getlistDetailProductRam(id)
        ) {
            listRam.add(ramService.findById(x).get());
        }for (int x: productService.getlistDetailProductCapacity(id)
        ) {
            listCapa.add(capacityService.findById(x).get());
        }for (int x: productService.getlistDetailProductColor(id)
        ) {
            listColor.add(colorService.findById(x).get());
        }
        ProductDetailDTO detailProduct=new ProductDetailDTO();
        detailProduct.setId(id);
        detailProduct.setName(categoryService.findById(id).get().getName());
        detailProduct.setRam(listRam);
        detailProduct.setCapa(listCapa);
        detailProduct.setColor(listColor);
        detailProduct.setPriceMin(getMaxMinPriceProduct(id).get(0));
        detailProduct.setPriceMax(getMaxMinPriceProduct(id).get(1));

        return detailProduct;
    }

    @RequestMapping("/product/getallproduct")
    public List<ProductDetailDTO> getallProduct(){
        List<ProductDetailDTO> listgetAllProduct= new ArrayList<>();
        for (int x: productService.getlistDetailProductCategory()
        ) {
            listgetAllProduct.add(getDetailProduct(x));
        }
        return listgetAllProduct;
    }
    private List<BigDecimal> getMaxMinPriceProduct(Integer id){
        return productService.getMinMaxPrice(id);
    }

    @RequestMapping("/saleapply/{id}")
    public Sale findSaleApply(@PathVariable("id") Integer id){
        return saleSV.findByid(saleSV.findSaleApply(id));
    }


}
