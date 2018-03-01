package com.ctl.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ctl.model.Equipamento;
import com.ctl.model.FormEquipamento;
import com.ctl.repository.EquipamentoRepository;
import com.ctl.repository.FabricanteRepository;
import com.ctl.repository.FeaturesRepository;
import com.ctl.repository.HomologadoRepository;
import com.ctl.repository.PrecificacaoRepository;
import com.ctl.repository.TipoRepository;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = {"/equipamento"})
public class EquipamentoController {

    @Autowired
    private EquipamentoRepository equipamentoRepository;
    private FabricanteRepository fabricanteRepository;
    private FeaturesRepository featuresRepository;
    private PrecificacaoRepository precificacaoRepository;
    private TipoRepository tipoRepository;
    private HomologadoRepository homologadoRepository;

    private static String uploadDir = System.getProperty("user.dir") + "/uploads/";

    public EquipamentoController(EquipamentoRepository equipamentoRepository, FabricanteRepository fabricanteRepository, FeaturesRepository featuresRepository, PrecificacaoRepository precificacaoRepository, TipoRepository tipoRepository, HomologadoRepository homologadoRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.fabricanteRepository = fabricanteRepository;
        this.featuresRepository = featuresRepository;
        this.precificacaoRepository = precificacaoRepository;
        this.tipoRepository = tipoRepository;
        this.homologadoRepository = homologadoRepository;
    }

    @GetMapping
    public String list(@RequestParam(required=false, value="segmento") String segmento,
                       @RequestParam(required=false, value="tipo") List<Long> tipo, Model model) {
        
        // Escolhendo o Segmento (ex.: B2B, B2C ou ambos)
        if(segmento == null && tipo == null){
            return "equipamento/segmento";
            
         // Segmento e tipo escolhidos
        }else if(segmento != null && tipo != null){
            model.addAttribute("equipamento", new Equipamento());
            model.addAttribute("equipamentos", equipamentoRepository.findBySegmentoAndTipo_idIn(segmento, tipo));
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
    public String edit(Model model, @RequestParam Long id) {
    	FormEquipamento form = new FormEquipamento();
    	form.setEquipamento(equipamentoRepository.findOne(id));
        model.addAttribute("equipamento", form);
        model.addAttribute("fabricantes", fabricanteRepository.findAll());
        model.addAttribute("featuress", featuresRepository.findAll());
        model.addAttribute("precificacaos", precificacaoRepository.findAll());
        model.addAttribute("tipos", tipoRepository.findAll());
        model.addAttribute("homologados", homologadoRepository.findAll());
        return "equipamento/formulario";
    }

    @GetMapping("/view")
    public String view(Model model, @RequestParam Long id) {
        String[] anexos = new File(uploadDir + id.toString() + "/anexos").list();
        model.addAttribute("anexos", anexos);
        model.addAttribute("equipamento", equipamentoRepository.findOne(id));
        model.addAttribute("fabricantes", fabricanteRepository.findAll());
        model.addAttribute("featuress", featuresRepository.findAll());
        model.addAttribute("precificacaos", precificacaoRepository.findAll());
        model.addAttribute("tipos", tipoRepository.findAll());
        model.addAttribute("homologados", homologadoRepository.findAll());
        return "equipamento/descricao";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("equipamento", new FormEquipamento());
        model.addAttribute("fabricantes", fabricanteRepository.findAll());
        model.addAttribute("featuress", featuresRepository.findAll());
        model.addAttribute("precificacaos", precificacaoRepository.findAll());
        model.addAttribute("tipos", tipoRepository.findAll());
        model.addAttribute("homologados", homologadoRepository.findAll());
        return "equipamento/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("equipamento") FormEquipamento equipamento,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("equipamento", new FormEquipamento());
            model.addAttribute("fabricantes", fabricanteRepository.findAll());
            model.addAttribute("featuress", featuresRepository.findAll());
            model.addAttribute("precificacaos", precificacaoRepository.findAll());
            model.addAttribute("tipos", tipoRepository.findAll());
            model.addAttribute("homologados", homologadoRepository.findAll());
            return "equipamento/formulario";
        }

        try {
            equipamento.setEquipamento(equipamentoRepository.save(equipamento.getEquipamento()));
            String path = uploadDir + equipamento.getEquipamento().getId();
            // Abrindo a pasta, e criando se não existe
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }

            // salvando imagem
            String imgType = equipamento.getImagem().getOriginalFilename();
            int pointer = imgType.lastIndexOf(".");
            if (pointer == -1) {
                throw new Exception("Imagem não inserida");
            }
            imgType = imgType.substring(pointer);
            File img = new File(path + "/image");// + imgType);
            img.createNewFile();
            equipamento.getImagem().transferTo(img);

            // verifica se o usuário inseriu o caderno e o dataSheet
            // todo: validar formulário
            if (equipamento.getCaderno().getOriginalFilename().isEmpty()
                    && equipamento.getDataSheet().getOriginalFilename().isEmpty()) {
                throw new Exception("Caderno e DataSheet é obrigatorio");
            }

            // salvando caderno de Testes
            File caderno = new File(path + "/caderno.pdf");
            caderno.createNewFile();
            equipamento.getCaderno().transferTo(caderno);

            // salvando dataSheet
            File dataSheet = new File(path + "/dataSheet.pdf");
            dataSheet.createNewFile();
            equipamento.getDataSheet().transferTo(dataSheet);

            // Salvando anexos
            if (equipamento.getFiles().length > 0) {
                new File(path + "/anexos").mkdirs();
                for (MultipartFile file : equipamento.getFiles()) {
                    file.transferTo(new File(path + "/anexos/" + file.getOriginalFilename()));
                }
            }

        } catch (Exception e) {
            File directory = new File(uploadDir + equipamento.getEquipamento().getId());
            try {
                FileUtils.cleanDirectory(directory);
                FileUtils.forceDelete(directory);
            } catch (Exception ex) {
            }
            equipamentoRepository.delete(equipamento.getEquipamento());
            e.printStackTrace();
            model.addAttribute("equipamento", new FormEquipamento());
            model.addAttribute("fabricantes", fabricanteRepository.findAll());
            model.addAttribute("featuress", featuresRepository.findAll());
            model.addAttribute("precificacaos", precificacaoRepository.findAll());
            model.addAttribute("tipos", tipoRepository.findAll());
            model.addAttribute("homologados", homologadoRepository.findAll());
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
    public String buscar(Model model, @RequestParam String nome) {
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

}
