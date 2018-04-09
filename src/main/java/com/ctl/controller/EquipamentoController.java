package com.ctl.controller;

import javax.validation.Valid;

import com.ctl.model.Fabricante;
import com.ctl.model.User;
import com.ctl.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ctl.model.Equipamento;
import com.ctl.util.AdvancedSearchUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import form.SearchForm;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = {"/equipamento"})
public class EquipamentoController {

    @Autowired
    private EquipamentoRepository equipamentoRepository;
    
    @Autowired
    private FabricanteRepository fabricanteRepository;
    
    @Autowired
    private FeaturesRepository featuresRepository;
    
    @Autowired
    private PrecificacaoRepository precificacaoRepository;
    
    @Autowired
    private TipoRepository tipoRepository;
    
    @Autowired
    private HomologadoRepository homologadoRepository;
    
    @Autowired
    private  AdvancedSearchUtil advancedSearch;
    
    @Autowired
    private EquipamentoSearchRepositoryImpl repo;

    @Autowired
    private RequisitoRepository requisitoRepository;

    @Autowired
    private UserRepository userRepository;
    private static String uploadDir = System.getProperty("user.dir") + "/uploads/";
    
    @GetMapping
    public String list(@RequestParam(required=false, value="segmento") String segmento,
                       @RequestParam(required=false, value="tipo") List<Integer> tipo, Model model,
                       Authentication auth) {
        
        model = advancedSearch.build(model,auth);
        // Escolhendo o Segmento (ex.: B2B, B2C ou ambos)
        if(segmento == null && tipo == null){
            return "equipamento/segmento";
            
         // Segmento e tipo escolhidos
        }else if(segmento != null && tipo != null){
            SearchForm search = new SearchForm();
            List<String> seg = new ArrayList<>();
            seg.add(segmento);
            search.setSegmento(seg);
            search.setTipos(tipo);
            User user = userRepository.findByDeletedFalseAndEmailIgnoreCase(auth.getName());
            Fabricante fabri = user.getFabricante();

            if(fabri != null){
                ArrayList<Integer> f = new ArrayList<>();
                f.add(fabri.getId().intValue());
                search.setFabricantes(f);
            }
            model.addAttribute("equipamento", new Equipamento());
            model.addAttribute("equipamentos", repo.search(search));
            return "equipamento/listar";   
            
         // em caso de alguma falha retorna o usuário para escolher o segmento
         // ex.:(caso o usuário tente inserir o tipo sem escolher o segmento, modificando a url)
        }else if(segmento == null && tipo != null){
            return "redirect:/equipamento";
            
         // Escolhe o Tipo de equipamento
        }else{
            model.addAttribute("tipos", tipoRepository.findAll());
            model.addAttribute("tipoSearch", new HashSet<String>());
            return "equipamento/tipo";
        }
        }

    @GetMapping("/editar")
    public String edit(Model model, @RequestParam Long id, Authentication auth) {
        model = advancedSearch.build(model, auth);
        Equipamento form = new Equipamento();
        form = equipamentoRepository.findOne(id);
        String[] anexos = new File(uploadDir + id.toString() + "/anexos").list();
        // NULL SAFE
        if(anexos==null){
            anexos = new String[0];
        }
        System.out.println(anexos);
        model.addAttribute("anexos", anexos);
        model.addAttribute("equipamento", form);
        model.addAttribute("fabricantes", fabricanteRepository.findAll());
        model.addAttribute("featuress", featuresRepository.findAll());
        model.addAttribute("precificacaos", precificacaoRepository.findAll());
        model.addAttribute("tipos", tipoRepository.findAll());
        model.addAttribute("editar", true);
        model.addAttribute("homologados", homologadoRepository.findAll());
        model.addAttribute("requisitos", requisitoRepository.findAll());
        return "equipamento/formulario";
    }

    @GetMapping("/view")
    public String view(Model model, @RequestParam Long id, Authentication auth) {
        model = advancedSearch.build(model, auth);
        String[] anexos = new File(uploadDir + id.toString() + "/anexos").list();
        // NULL SAFE
        if(anexos==null){
            anexos = new String[0];
        }
        Equipamento e = equipamentoRepository.findOne(id);
        if(e==null)
            return "redirect:/equipamento";
        model.addAttribute("anexos", anexos);
        model.addAttribute("equipamento", e);
        return "equipamento/descricao";
    }

    @GetMapping("/novo")
    public String novo(Model model, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("equipamento", new Equipamento());
        model.addAttribute("fabricantes", fabricanteRepository.findAll());
        model.addAttribute("featuress", featuresRepository.findAll());
        model.addAttribute("precificacaos", precificacaoRepository.findAll());
        model.addAttribute("tipos", tipoRepository.findAll());
        model.addAttribute("editar", false);
        model.addAttribute("homologados", homologadoRepository.findAll());
        model.addAttribute("requisitos", requisitoRepository.findAll());
        return "equipamento/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@RequestParam(value = "editar", required = false) boolean editar,
                         @Valid @ModelAttribute("equipamento") Equipamento equipamento,
                         BindingResult bindingResult, Model model, Authentication auth) {
        model = advancedSearch.build(model, auth);
        if (bindingResult.hasErrors()) {
            model.addAttribute("equipamento", equipamento);
            model.addAttribute("fabricantes", fabricanteRepository.findAll());
            model.addAttribute("featuress", featuresRepository.findAll());
            model.addAttribute("precificacaos", precificacaoRepository.findAll());
            model.addAttribute("tipos", tipoRepository.findAll());
            model.addAttribute("homologados", homologadoRepository.findAll());
	        model.addAttribute("requisitos", requisitoRepository.findAll());
	        model.addAttribute("editar",editar);
	        model.addAttribute("anexos",new String[0]);
            return "equipamento/formulario";
        }

        try {
            //equipamento.setEquipamento(equipamentoRepository.save(equipamento.getEquipamento()));
            equipamento = equipamentoRepository.save(equipamento);
            String path = uploadDir + equipamento.getId();
            // Abrindo a pasta, e criando se não existe
            File f = new File(path);
            if (f!=null && !f.exists()) {
                f.mkdirs();
            }
            if(equipamento.getImagem() != null && !equipamento.getImagem().isEmpty()){
            // salvando imagem
                String imgType = equipamento.getImagem().getOriginalFilename();
                int pointer = imgType.lastIndexOf(".");
                if (pointer == -1) {
                    throw new Exception("Imagem não inserida");
                }

                imgType = imgType.substring(pointer);
                File img = new File(path + "/image");
                img.createNewFile();
                equipamento.getImagem().transferTo(img);

            }

            if(equipamento.getCaderno() != null && !equipamento.getCaderno().isEmpty()){
                // salvando caderno de Testes
                File caderno = new File(path + "/caderno.pdf");
                caderno.createNewFile();
                equipamento.getCaderno().transferTo(caderno);
            }

            if(equipamento.getCaderno() != null &&!equipamento.getCaderno().isEmpty()){
                // salvando dataSheet
                File dataSheet = new File(path + "/dataSheet.pdf");
                dataSheet.createNewFile();
                equipamento.getDataSheet().transferTo(dataSheet);
            }
            // Salvando anexos
            File dir = new File(path + "/anexos");
            if(!dir.exists())
                dir.mkdirs();
            if(equipamento.getFiles() != null) {
                for (MultipartFile file : equipamento.getFiles()) {
                    if (!file.isEmpty())
                        file.transferTo(new File(path + "/anexos/" + file.getOriginalFilename()));
                }
            }
            String names = equipamento.getFilesName();
            if(names!=null && names.length() > 0){
                if(names.contains(",")){
                    String files[] = names.split(",");
                    for(String n: files){
                        FileUtils.forceDelete(new File(path+"/anexos/"+n));
                    }
                }else{
                    FileUtils.forceDelete(new File(path+"/anexos/"+names));
                }
            }

        } catch (Exception e) {
            File directory = new File(uploadDir + equipamento.getId());
            if(!editar){
                try {
                    FileUtils.cleanDirectory(directory);
                    FileUtils.forceDelete(directory);
                } catch (Exception ex) {
                }
                equipamentoRepository.delete(equipamento);
            }
            e.printStackTrace();
            model.addAttribute("equipamento", equipamento);
            model.addAttribute("fabricantes", fabricanteRepository.findAll());
            model.addAttribute("featuress", featuresRepository.findAll());
            model.addAttribute("precificacaos", precificacaoRepository.findAll());
            model.addAttribute("tipos", tipoRepository.findAll());
            model.addAttribute("homologados", homologadoRepository.findAll());
	        model.addAttribute("requisitos", requisitoRepository.findAll());
            model.addAttribute("editar",editar);
            return "equipamento/formulario";
//                    return "equipamento/listar";

        }
//                return "redirect:/"; 
        return "redirect:/equipamento";
    }

    @GetMapping("/excluir")
    public String excluir(Model model, @RequestParam Long id) {
        equipamentoRepository.delete(id);
        return "redirect:/";
    }

    @GetMapping("/buscar")
    public String buscar(Model model, @RequestParam String nome, Authentication auth) {
        model = advancedSearch.build(model, auth);
        model.addAttribute("equipamento", new Equipamento());
        model.addAttribute("equipamentos", equipamentoRepository.findByNomeLike("%" + nome + "%"));
        return "equipamento/formulario";
    }

    @GetMapping("/image/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable("imageName") Integer fileName) {
        try {
            File file = new File(uploadDir + fileName + "/image");
            return Files.readAllBytes(file.toPath());
        } catch (IOException ex) {
            Logger.getLogger(EquipamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GetMapping("/pdf/{type}/{imageName}")
    @ResponseBody
    public ResponseEntity<byte[]> getPDF(@PathVariable("imageName") Integer fileName, @PathVariable("type") Integer type) {
        try {
            File file;
            if (type == 1) {
                file = new File(uploadDir + fileName + "/dataSheet.pdf");
            } else {
                file = new File(uploadDir + fileName + "/caderno.pdf");
            }
            byte[] contents = Files.readAllBytes(file.toPath());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            String filename = "output.pdf";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
            return response;
        } catch (IOException ex) {
            Logger.getLogger(EquipamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GetMapping("/file/{id}/{fileName}.{type}")
    public void downloadFile(HttpServletResponse resonse, @PathVariable(value = "id") Integer id, @PathVariable(value = "fileName") String fileName,
            @PathVariable(value = "type") String tipo) {
        try {
            File file = new File(uploadDir + id.toString() + "/anexos/" + fileName + "." + tipo);
            resonse.setContentType("application/pdf");
            resonse.setHeader("Content-Disposition", "attachment;filename=" + fileName + "." + tipo);
            BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(file));
            BufferedOutputStream outStream = new BufferedOutputStream(resonse.getOutputStream());

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = inStrem.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            outStream.flush();
            inStrem.close();
        } catch (IOException ex) {
            Logger.getLogger(EquipamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @GetMapping("/search")
    public String search(Model model, Authentication auth, SearchForm s){
        User user = userRepository.findByDeletedFalseAndEmailIgnoreCase(auth.getName());
        Fabricante fabri = user.getFabricante();

        if(fabri != null){
            ArrayList<Integer> f = new ArrayList<>();
            f.add(fabri.getId().intValue());
            s.setFabricantes(f);
        }
        model = advancedSearch.build(model, auth);
        model.addAttribute("equipamento", new Equipamento());
        model.addAttribute("equipamentos", repo.search(s));
        return "equipamento/listar";
    }
}
