package com.fix.mobile.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.fix.mobile.dto.ColorProductResponDTO;
import com.fix.mobile.dto.ProductDetailDTO;
import com.fix.mobile.dto.ProductResponDTO;
import com.fix.mobile.entity.*;
import com.fix.mobile.repository.*;
import com.fix.mobile.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import com.fix.mobile.repository.ProductRepository;
import com.fix.mobile.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Autowired
    private ImayProductRepository imayProductRepository;

    @Autowired
    private CapacityRepository capacityRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private RamRepository ramRepository;

    @Autowired
    private CategoryRepository cateRepository;
    @Autowired
    private OrderRepository orderRepository;


    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product entity) {
        return repository.save(entity);
    }

    @Override
    public List<Product> save(List<Product> entities) {
        return (List<Product>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>) repository.findAll();
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        Page<Product> entityPage = repository.findAll(pageable);
        List<Product> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Product update(Product entity, Integer id) {
        Optional<Product> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
    @Override
    public Page<Product> findShowSale(int pageNumber, int maxRecord, String share) {
        Pageable pageable = PageRequest.of(pageNumber, maxRecord);
        Page<Product> pageProduct = repository.findShowSale(share, pageable);
        return pageProduct;
     }
     
     @Override 
    public Page<Product> getAll(Pageable page) {
        return repository.findAll(page);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return repository.findByName(name);
    }

    public List<ProductResponDTO> findByCategoryAndStatus(Integer id) {
        int totail;
        List<Product> products= repository.getProductByCategoryIdAndStatus(id,  1);
        System.out.println(products.size());
        List<ProductResponDTO> productResponDTOList = new ArrayList<>();
            for (int i = 0; i < products.size(); i++) {
                ProductResponDTO productResponDTO = new ProductResponDTO();
                System.out.println(i);
                Product productImage = repository.findById(products.get(i).getIdProduct()).orElse(null);
                List<ImayProduct> imeis = imayProductRepository.findByProductAndStatus(products.get(i), 1);
                totail = imeis.size();
                productResponDTO.setIdProduct(products.get(i).getIdProduct());
                productResponDTO.setName(products.get(i).getName());
                productResponDTO.setCategory(products.get(i).getCategory());
                if(productImage != null && productImage.getImages().size() > 0){
                    productResponDTO.setImage(productImage.getImages().get(0).getName());
                } else productResponDTO.setImage("https://res.cloudinary.com/dcll6yp9s/image/upload/v1669970939/fugsyd4nw4ks0vb7kzyd.png");
                productResponDTO.setPrice(products.get(i).getPrice());
                productResponDTO.setRam(products.get(i).getRam());
                productResponDTO.setCapacity(products.get(i).getCapacity());
                productResponDTO.setColor(products.get(i).getColor());
                productResponDTO.setTotail(totail);

                productResponDTOList.add(productResponDTO);
            }
            return productResponDTOList;
    }

    @Override
    public List<Product> findByProductLimit() {
        return repository.findByProductLimit();
    }

    @Override
    public List<Product> findByProductLitmitPrice() {
        return repository.findByProductLitmitPrice();
    }

    @Override
    public List<Product> findByNameAndCapacityAndColor(String name, Integer capacity, Integer color) {
        Color colorfind = colorRepository.findById(color).orElse(null);
        Capacity capacityfind = capacityRepository.findById(capacity).orElse(null);
        return repository.findByNameAndCapacityAndColor(name, capacityfind, colorfind);
    }

    @Override
    public List<ColorProductResponDTO> getColorProductByName(String name) {
        List<Product> productList = repository.findColorByNameProduct(name);
        List<ColorProductResponDTO> colorProductResponDTOList = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            ColorProductResponDTO colorProductResponDTO = new ColorProductResponDTO();
            colorProductResponDTO.setColor(productList.get(i).getColor().getIdColor());
            colorProductResponDTOList.add(colorProductResponDTO);
        }
        return colorProductResponDTOList;
    }

    @Override
    public List<ProductResponDTO> getProductCount() {
        int totail;
        List<Product> productList = repository.findByProductLimit();
        List<ProductResponDTO> productResponDTOList = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            ProductResponDTO productResponDTO = new ProductResponDTO();
            Product productImage = repository.findById(productList.get(i).getIdProduct()).orElse(null);
            List<ImayProduct> imeis = imayProductRepository.findByProductAndStatus(productList.get(i), 1);
             totail = imeis.size();
             productResponDTO.setIdProduct(productList.get(i).getIdProduct());
             productResponDTO.setName(productList.get(i).getName());
             productResponDTO.setCategory(productList.get(i).getCategory());
            if(productImage != null && productImage.getImages().size() > 0){
                productResponDTO.setImage(productImage.getImages().get(0).getName());
            } else productResponDTO.setImage("https://res.cloudinary.com/dcll6yp9s/image/upload/v1669970939/fugsyd4nw4ks0vb7kzyd.png");
            productResponDTO.setPrice(productList.get(i).getPrice());
            productResponDTO.setTotail(totail);
            productResponDTOList.add(productResponDTO);
        }
        return productResponDTOList;
    }

    @Override
    public List<ProductResponDTO> findByPriceExits() {
        int totail;
        List<Product> productList = repository.findByProductLitmitPrice();
        List<ProductResponDTO> productResponDTOList = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            ProductResponDTO productResponDTO = new ProductResponDTO();
            Product productImage = repository.findById(productList.get(i).getIdProduct()).orElse(null);
            List<ImayProduct> imeis = imayProductRepository.findByProductAndStatus(productList.get(i), 1);
            totail = imeis.size();
            productResponDTO.setIdProduct(productList.get(i).getIdProduct());
            productResponDTO.setName(productList.get(i).getName());
            if(productImage != null && productImage.getImages().size() > 0){
                productResponDTO.setImage(productImage.getImages().get(0).getName());
            } else productResponDTO.setImage("https://res.cloudinary.com/dcll6yp9s/image/upload/v1669970939/fugsyd4nw4ks0vb7kzyd.png");
            productResponDTO.setPrice(productList.get(i).getPrice());
            productResponDTO.setTotail(totail);
            productResponDTOList.add(productResponDTO);
        }
        return productResponDTOList;
    }

    @Override
    public List<Product> findProductByPrices() {
        return repository.findProductByPrices();
    }

    @Override
    public Product getdeTailPrd(Integer idCapa, Integer idRam, Integer idColor) {
        Product prd = new Product();
        if (repository.getDetailPrd(idCapa,idRam,idColor)!=null){
            if(imayProductRepository.findByProductAndStatus(repository.getDetailPrd(idCapa,idRam,idColor),1)==null){
                prd = null;
            }
            else{
                prd = repository.getDetailPrd(idCapa,idRam,idColor);
            }
        }else{
            prd = null;
        }
        return prd;
    }



    @Override
    public ProductDetailDTO getDetailProduct(Integer id) {
        List<Ram> listRam=new ArrayList<>();
        List<Capacity> listCapa=new ArrayList<>();
        List<Color> listColor=new ArrayList<>();
        List<Integer> listIImage = new ArrayList<>();
        List<String> listimage= new ArrayList<>();
        for (int x: repository.getlistDetailProductRam(id)
        ) {
            listRam.add(ramRepository.findById(x).get());
        }for (int x: repository.getlistDetailProductCapacity(id)
        ) {
            listCapa.add(capacityRepository.findById(x).get());
        }for (int x: repository.getlistDetailProductColor(id)
        ) {
            listColor.add(colorRepository.findById(x).get());
        }for (int x: repository.getIdimage(id)
        ) {
            listIImage.add(x);
        }
        if(listIImage.size()!=0){
            for(int i =0;i<listIImage.size();i++){
                if(repository.getImg(listIImage.get(i))!=null){
                    listimage.add(repository.getImg(listIImage.get(i)));
                }
            }
        }

        ProductDetailDTO detailProduct=new ProductDetailDTO();
        detailProduct.setId(id);
        detailProduct.setName(cateRepository.findById(id).get().getName());
        detailProduct.setRam(listRam);
        detailProduct.setCapa(listCapa);
        detailProduct.setColor(listColor);
        detailProduct.setImages(listimage);
        detailProduct.setPriceMin(getMaxMinPriceProduct(id).get(0));
        detailProduct.setPriceMax(getMaxMinPriceProduct(id).get(1));
        return detailProduct;
    }

    @Override
    public void deleteIFSaleEnd(Integer id) {
        repository.deleteIFsaleEnd(id);
        orderRepository.deleteById(id);
    }

    @Override
    public Page<ProductDetailDTO> getByPage(int pageNumber, int maxRecord, JsonNode findProcuctAll) {
        List<Product> listPrd = repository.findProduct();
        List<Product> listFindProduct = new ArrayList<>();
        JsonNode listIdRam = findProcuctAll.get("idRam");
        JsonNode listIdColer = findProcuctAll.get("idColer");
        JsonNode listIdCapacity = findProcuctAll.get("idCapacity");
        Integer sortMinMax = findProcuctAll.get("sortMinMax").asInt();
        if(listPrd.size()!=0) {
            for (int i = 0; i < listPrd.size(); i++) {
                if (
                        listIdRam.size() == 0 &&
                                listIdCapacity.size() == 0 &&
                                listIdColer.size() == 0
                ) {
                    listFindProduct = checklist(listFindProduct, listPrd.get(i));
                } else {

                    if (listIdRam.size() != 0) {
                        for (int y = 0; y < listIdRam.size(); y++) {
                            if (listIdRam.get(y).asInt() == listPrd.get(i).getRam().getIdRam()
                            ) {
                                listFindProduct = checklist(listFindProduct, listPrd.get(i));
                            }
                        }
                    }

                    if (listIdCapacity.size() != 0) {
                        for (int y = 0; y < listIdCapacity.size(); y++) {
                            if (listIdCapacity.get(y).asInt() == listPrd.get(i).getCapacity().getIdCapacity()
                            ) {
                                listFindProduct = checklist(listFindProduct, listPrd.get(i));
                            }
                        }
                    }

                    if (listIdColer.size() != 0) {
                        for (int y = 0; y < listIdColer.size(); y++) {
                            if (listIdColer.get(y).asInt() == listPrd.get(i).getColor().getIdColor()
                            ) {
                                listFindProduct = checklist(listFindProduct, listPrd.get(i));
                            }
                        }
                    }
                }
            }
        }
        List<ProductDetailDTO> listgetAllProduct= new ArrayList<>();
        for (int x: repository.getlistDetailProductCategory()
        ) {
            if(listFindProduct.size()!=0){
                for (int i=0;i<listFindProduct.size();i++){
                    if(listFindProduct.get(i).getCategory().getIdCategory()==x){
                        if(listgetAllProduct.size()==0){
                            listgetAllProduct.add(getDetailProduct(x));
                        }else {
                            boolean ktt=true;
                            for (int y=0; y < listgetAllProduct.size();y++
                            ) {
                                if (listgetAllProduct.get(y).getId() == x) {
                                    ktt=false;
                                }
                            }
                            if(ktt){
                                listgetAllProduct.add(getDetailProduct(x));
                            }
                        }
                    }
                }
            }
        }

        BigDecimal priceSalemin;
        BigDecimal priceSalemax;
        List<ProductDetailDTO> listRmove=new ArrayList<>();
        if(!String.valueOf(findProcuctAll.get("priceSalemin")).replaceAll("\"","").equals("")){
            priceSalemin = new BigDecimal(String.valueOf(findProcuctAll.get("priceSalemin")).replaceAll("\"",""));
            if(!String.valueOf(findProcuctAll.get("priceSalemax")).replaceAll("\"","").equals("")){
                priceSalemax = new BigDecimal(String.valueOf(findProcuctAll.get("priceSalemax")).replaceAll("\"",""));
                for (int i = 0; i <listgetAllProduct.size(); i++
                ) {
                    if(listgetAllProduct.get(i).getPriceMin().compareTo(priceSalemax)>0){
                        listRmove.add(listgetAllProduct.get(i));
                    }else if(listgetAllProduct.get(i).getPriceMax().compareTo(priceSalemin)<0){
                        listRmove.add(listgetAllProduct.get(i));
                    }
                }
            }
        }
        if(listgetAllProduct.size()!=0){
            for(int i=0; i<listgetAllProduct.size();i++){
                if(!listgetAllProduct.get(i).getName().contains(String.valueOf(findProcuctAll.get("search")).replaceAll("\"", "").toLowerCase())){
                    if(listRmove.size()==0){
                        listRmove.add(listgetAllProduct.get(i));
                    }else{
                        boolean cc=true;
                        for(int x=0;x<listRmove.size();x++){
                            if (i==x) {
                                cc=false;
                            }
                        }
                        if(cc){
                            listRmove.add(listgetAllProduct.get(i));
                        }
                    }
                }
            }
        }
        if(listRmove!=null){
            listgetAllProduct.removeAll(listRmove);
        }
        Pageable paging = PageRequest.of(pageNumber, maxRecord);
        int start = Math.min((int)paging.getOffset(), listgetAllProduct.size());
        int end = Math.min((start + paging.getPageSize()), listgetAllProduct.size());
        Page<ProductDetailDTO> pageFindProductAll = new PageImpl<>(sortProduct(listgetAllProduct,sortMinMax), paging, listFindProduct.size());
        return pageFindProductAll;
    }
    private List<BigDecimal> getMaxMinPriceProduct(Integer id){
        List<BigDecimal> listPriceMINMAX= new ArrayList<>();
        listPriceMINMAX.add(repository.getMinPrice(id));
        listPriceMINMAX.add(repository.getMaxPrice(id));
        return listPriceMINMAX;
    }

    private List<Product> checklist( List<Product> listcheck, Product check){
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
    private List<ProductDetailDTO> sortProduct(List<ProductDetailDTO> listPRD,Integer check) {
        if(listPRD.size()==0){
            return listPRD;
        }else {
            Comparator<ProductDetailDTO> MinMax = new Comparator<ProductDetailDTO>() {
                @Override
                public int compare(ProductDetailDTO o1, ProductDetailDTO o2) {
                    return o1.getPriceMin().compareTo(o2.getPriceMin());
                }
            };
            Comparator<ProductDetailDTO> MaxMin = new Comparator<ProductDetailDTO>() {
                @Override
                public int compare(ProductDetailDTO o1, ProductDetailDTO o2) {
                    return o2.getPriceMin().compareTo(o1.getPriceMin());
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
    private List<ProductDetailDTO> getallProduct(){
        List<ProductDetailDTO> listgetAllProduct= new ArrayList<>();
        for (int x: repository.getlistDetailProductCategory()
        ) {
            listgetAllProduct.add(getDetailProduct(x));
        }
        return listgetAllProduct;
    }

}
