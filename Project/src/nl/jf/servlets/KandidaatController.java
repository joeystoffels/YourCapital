package nl.jf.servlets;

import javax.swing.JOptionPane;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import nl.jf.yc.Kandidaat;
import nl.jf.yc.KandidaatDao;
import nl.jf.yc.Skill;
import nl.jf.yc.SkillDao;

@SessionAttributes("Kandidaat")
@Controller
public class KandidaatController {

	@ModelAttribute("kandidaat")
	public Kandidaat createKandidaat() {
		return new Kandidaat();
	}

	@RequestMapping("/home")
	public String homeMvc(Model model) {
		model.addAttribute("kandidaten", KandidaatDao.all());
		return "MainHtml";
	}

	@RequestMapping("/carousel")
	public String carousel(Model model, String naam, String leeftijd, String woonplaats, String foto) {
		model.addAttribute("kandidaten", KandidaatDao.all());
		model.addAttribute("namen", naam);
		model.addAttribute("leeftijd", leeftijd);
		model.addAttribute("woonplaats", woonplaats);
		model.addAttribute("foto", foto);
		return "index";
	}

	/*
	 * @RequestMapping(value="/zoek", method=RequestMethod.GET) public String
	 * zoekhtml(Model model, String naam){ model.addAttribute("kandidaten",
	 * KandidaatDao.all()); model.addAttribute("namen", naam); return
	 * "ZoekHtml"; }
	 */

	/*
	 * @RequestMapping(value="/test", method=RequestMethod.GET)
	 * public @ResponseBody String test(){ SkillDao.addSkill(2L,2L); return
	 * "Ok"; }
	 */

	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public String create(Model model, String naam, String leeftijd, String woonplaats, String foto) {
		
		foto = "http://10.2.22.50/Project/resources/img/" + ((int)(1 + (Math.random() * (63 - 1)))) + ".jpg";
		KandidaatDao.create(naam, leeftijd, woonplaats, foto);
		/*try {
			Integer.parseInt(leeftijd);
		} catch (NumberFormatException e) {
			// JOptionPane.showMessageDialog(null, "Geen geldige invoer!",
			// "Error", JOptionPane.ERROR_MESSAGE);
			// model.addAttribute("error", "error msg");
			return "error";
		}

		int x = Integer.parseInt(leeftijd);

		if (naam.equals("") || (naam.length() >= 25)) {
			// JOptionPane.showMessageDialog(null, "Geen naam ingevuld!",
			// "Error", JOptionPane.ERROR_MESSAGE);
			return "error";
		} else if (!(x < 68 && x > 16)) {
			// JOptionPane.showMessageDialog(null, "Geen geldige leeftijd!",
			// "Error", JOptionPane.ERROR_MESSAGE);
			return "error";
		} else if (woonplaats instanceof String && !(woonplaats.equals("")) && (woonplaats.length() <= 25)) {
			
		} else {// JOptionPane.showMessageDialog(null, "Ongeldige invoer
				// woonplaats!", "Error", JOptionPane.ERROR_MESSAGE);
			return "error";
		}*/

		return "redirect:/home";
	}

	@RequestMapping(value = "/zoek", method = RequestMethod.GET)
	public String search(Model model, String naam, String leeftijd, String woonplaats) {
		model.addAttribute("kandidaten", KandidaatDao.all());
		model.addAttribute("namen", naam);
		model.addAttribute("leeftijd", leeftijd);
		model.addAttribute("woonplaats", woonplaats);
		return "ZoekHtml";
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteView(@PathVariable String id) {
		Long key;
		try {
			key = Long.valueOf(id);
		} catch (NumberFormatException e) {
			// id is geen getal? error 404
			return null;
		}
		KandidaatDao.remove(key);
		return "redirect:/home";
	}

	@RequestMapping(value = "/deleteskill/{sid}/{kid}")
	public String deleteSkill(@PathVariable String sid, @PathVariable String kid) {
		Long key;
		try {
			key = Long.valueOf(sid);
		} catch (NumberFormatException e) {
			// id is geen getal? error 404
			return null;
		}
		SkillDao.remove(key);
		return "redirect:/kandidaat/{kid}";
	}

	@RequestMapping(value = "/kandidaat/{id}")
	public String detailView(@PathVariable long id, Model model) {
		Long key;
		model.addAttribute("skills", SkillDao.all(id));
		try {
			key = Long.valueOf(id);
		} catch (NumberFormatException e) {
			// id is geen getal? error 404
			return null;
		}
		Kandidaat k = KandidaatDao.find(key);
		if (k == null) {
			// geen kandidaat met gegeven id? error 404
			return null;
		} else {
			model.addAttribute("kandidaat", k);
			return "kandidaatdetail";
		}
	}

	@RequestMapping(value = "/kandidaat/{id}", method = RequestMethod.POST)
	public String createSkill(@PathVariable long id, Model model, String naam) {
		Skill s = SkillDao.create(naam, id);
		SkillDao.addSkill(id, s.getId());

		// SkillDao.addSkill(x, id, naam);
		return "redirect:/kandidaat/{id}";

	}
}
